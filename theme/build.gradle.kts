import com.guru.composecookbook.plugin.dependencies.addComposeOfficialDependencies
import com.guru.composecookbook.plugin.dependencies.addComposeThirdPartyDependencies
import com.guru.composecookbook.plugin.dependencies.addCoreAndroidUiDependencies

plugins {
    /**
     * See [common-compose-module-configs-script-plugin.gradle.kts] file
     */
    id("common-compose-module-configs-script-plugin")
}

dependencies {
    addComposeOfficialDependencies()
    addComposeThirdPartyDependencies()
    addCoreAndroidUiDependencies()
}