import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.Dimension
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.ie.InternetExplorerDriver

import java.io.File
import org.apache.commons.lang.SystemUtils

waiting {
  timeout = 10
  retryInterval = 0.5
  presets {
        slow {
            timeout = 20
            retryInterval = 1
        }
        quick {
            timeout = 5
        }
    }
}

atCheckWaiting = true
baseNavigatorWaiting = true

def downloadDriver = { File file, String path ->
    if (!file.exists()) {
        def ant = new AntBuilder()
        ant.get(src: path, dest: 'driver.zip')
        ant.unzip(src: 'driver.zip', dest: file.parent)
        ant.delete(file: 'driver.zip')
        ant.chmod(file: file, perm: '700')
    }
}

driver = {
    def d = new PhantomJSDriver(new DesiredCapabilities())
    d.manage().window().setSize(new Dimension(1024, 768))
    d
}

def browserStackBrowser = System.getProperty("geb.browserstack.browser")
if (browserStackBrowser) {
    driver = {
       def username = ""
       assert username
       def accessKey = ""
       assert accessKey
       new BrowserStackDriverFactory().create(browserStackBrowser, username, accessKey)
    }
}

