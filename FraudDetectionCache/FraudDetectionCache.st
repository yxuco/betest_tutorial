<?xml version="1.0" encoding="UTF-8"?><site xmlns="http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd" name="FraudDetection">
    <description/>
    <clusters>
        <cluster name="fdcache" project-cdd="/usr/local/tibco_be/be/5.2/examples/standard/FraudDetectionCache/FraudDetectionCache/fdcache.cdd">
            <master-files>
                <cdd-master>/usr/local/tibco_be/be/5.2/examples/standard/FraudDetectionCache/FraudDetectionCache/fdcache.cdd</cdd-master>               
                <ear-master>/usr/local/tibco_be/be/5.2/examples/standard/FraudDetectionCache/fdcache.ear</ear-master>
            </master-files>
            <run-version>
                <be-runtime version="5.2.0"/>
            </run-version>
            <deployment-units>
                <deployment-unit id="DU_1" name="DU_WindowsOS">
                    <deployed-files>
                        <cdd-deployed>C:\temp\fdcache.cdd</cdd-deployed>
                        <ear-deployed>C:\temp\fdcache.ear</ear-deployed>
                    </deployed-files>
                    <processing-units-config>
                        <processing-unit-config id="InferenceAgent" jmxport="5500" puid="default" use-as-engine-name="true"/>
                        <processing-unit-config id="CacheServer" jmxport="5501" puid="cache" use-as-engine-name="true"/>
                    </processing-units-config>
                </deployment-unit>
            </deployment-units>
            <deployment-mappings>
                <deployment-mapping deployment-unit-ref="DU_1" host-ref="HR_0"/>
            </deployment-mappings>
        </cluster>
    </clusters>
    <host-resources>
        <host-resource id="HR_0">
            <hostname>HOST_MACHINE_NAME</hostname>
            <ip>localhost</ip>
            <user-credentials password="" username=""/>
            <os-type>Windows</os-type>
            <software>
                <be version="5.2.0"><home>/usr/local/tibco_be/be/5.2</home><tra>/usr/local/tibco_be/be/5.2/bin/be-engine.tra</tra></be>
            </software>
            <start-pu-method>
                <ssh port="22"/>
            </start-pu-method>
        </host-resource>
    </host-resources>
</site>