# Creating Dialogs

Ready! API has a rather elaborate framework for building dialogs that look and behave consistently, relieving you 
of having to write code specifically for Swing components, layouts, etc. Using the framework is achieved by the 
following steps:

1. Define an annotated interface with constant members for each field in the form
2. When you need the dialog - build an instance of it using the ADialogBuilder and set initial values, 
 validators and listeners
3. Show the dialog and process its "results" when it is closed

Let's walk through a simple example taken from the [ExportSwaggerAction](https://github.com/olensmar/soapui-swagger-plugin/blob/master/src/main/groovy/com/smartbear/restplugin/ExportSwaggerAction.java) in the 
tex[Swagger Plugin](https://github.com/olensmar/soapui-swagger-plugin).

## 1. Define the interface

The ExportSwaggerAction prompts you to export a Swagger definition from a REST API in Ready! API. The Form for this 
is defined as follows:

```java
@AForm( name = "Export Swagger Definition", description = "Creates a Swagger definition for selected REST APIs in this project" )
public interface Form
{
    @AField( name = "APIs", description = "Select which REST APIs to include in the Swagger definition", type = AFieldType.MULTILIST )
    public final static String APIS = "APIs";

    @AField( name = "Target Folder", description = "Where to save the Swagger definition", type = AFieldType.FOLDER )
    public final static String FOLDER = "Target Folder";

    @AField( name = "API Version", description = "API Version", type = AFieldType.STRING )
    public final static String VERSION = "API Version";

    @AField( name = "Base Path", description = "Base Path that the Swagger definition will be hosted on", type = AFieldType.STRING )
    public final static String BASEPATH = "Base Path";

    @AField(name = "Swagger Version", description = "Select Swagger version", type = AFieldType.RADIOGROUP, values = {"1.2", "2.0"})
    public final static String SWAGGER_VERSION = "Swagger Version";

    @AField(name = "Format", description = "Select Swagger format", type = AFieldType.RADIOGROUP, values = {"json", "yaml", "xml"})
    public final static String FORMAT = "Format";
}
```

The key points being:

- The interface is annotated with the @AForm annotation which has attributes for the name and description of the dialog
- The interface contains a number of ```public final static String``` constants which are annotated with a corresponding 
@AField annotation. Note the following:
    - the name attribute *has* to have the exact same value as the value of the constant itself
    - the type attribute specifies which type of field to show - the default type is AFieldType.String
    - values can optionally be specified at design time using the values attribute
    
    
## 2. Build the dialog and set initial values, validators and listeners

When we need to display the actual dialog - we use the ADialogBuilder to build and initialize it;
 
```java
XFormDialog dialog = ADialogBuilder.buildDialog( Form.class );

dialog.setValue(Form.FORMAT, settings.getString(FORMAT, "json"));
dialog.setValue(Form.VERSION, settings.getString(VERSION, "1.0"));
dialog.setValue(Form.BASEPATH, settings.getString(BASE_PATH, "" ));
dialog.setValue(Form.FOLDER, settings.getString(TARGET_PATH, "" ));
dialog.setValue(Form.SWAGGER_VERSION, settings.getString(SWAGGER_VERSION, "2.0"));

XFormOptionsField apis = (XFormOptionsField) dialog.getFormField(Form.APIS);
apis.setOptions(...);
```

Note that the first argument to the setValue method is the constant for the corresponding field. Furthermore the 
actual field for the Form.APIS constant is retrieved and configured via the XFormOptionsField.setOptions(...) method.

We could further add XFormFieldValidators and XFormFieldListeners to desired fields to handle field-specific logic, for
example enable/disable other fields if a certain value is selected - or validate the input of a desired field to be 
in line with our requirements. 

## 3. Show the dialog and "use" its results

Once the dialog has been set up correctly - show it with

```java
if( dialog.show()) {
    // user pressed ok - get values and do something with them
    String version = dialog.getStringValue( Form.VERSION );
    ...
}
```

By default a dialog contains OK and Cancel buttons - if the user presses OK the dialog.show() method returns true. If 
you want to customize the actions of a dialog you can use the dialog.addAction(...) and dialog.getActionList() methods
accordingly.


