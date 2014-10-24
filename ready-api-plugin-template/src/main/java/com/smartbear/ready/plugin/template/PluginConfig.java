package com.smartbear.ready.plugin.template;

import com.eviware.soapui.plugins.PluginAdapter;
import com.eviware.soapui.plugins.PluginConfiguration;

@PluginConfiguration(groupId = "com.smartbear.ready.plugins",
        name = "Ready! API Plugin Template", version = "1.0",
        autoDetect = true, description = "A Sample Ready! API Plugin",
        infoUrl = "https://github.com/smartbear/ready-api-plugin-template")
public class PluginConfig extends PluginAdapter {
}
