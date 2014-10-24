package com.smartbear.ready.plugin.template

public class TemplateScript
{
    String str

    public TemplateScript( def str )
    {
        this.str = str;
    }

    public def format( def value )
    {
        return String.format( str, value )
    }
}