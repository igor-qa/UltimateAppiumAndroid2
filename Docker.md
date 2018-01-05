1. Download and begin installing Docker Toolbox with default settings,
**Stop** on **Quick Start** page - https://download.docker.com/mac/stable/DockerToolbox.pkg
(We don’t use Docker for Mac as it can’t passthrough USB ports)
2. Open **System Preferences** > **Security & Privacy**
3. Click **Allow System software from developer “Oracle America, Inc”** on **General** tab
4. Run **Docker Quickstart Terminal** from the installer, Press Ctrl+C once it hangs on **Waiting for SSH to be available**
5. Download and install latest version of VirtualBox - https://www.virtualbox.org/wiki/Downloads
6. In **Docker Quickstart Terminal** press Up arrow and then Enter to rerun the last command. Wait for it to complete. You should see message like **docker is configured to use the default machine with IP 192.168.99.100** in the end.
7. Click **Continue** in the installer. It’s ok to get message that the installation was failed.
8. Download and install (double click) **Oracle VM VirtualBox Extension Pack** - https://www.virtualbox.org/wiki/Downloads
9. Connect Android devices
10. Close **Docker Quickstart Terminal**
11. Start new normal Terminal session (Applications/Utilities/Terminal)
12. Follow steps of **Setting up Android real device test on Docker** section from page https://github.com/appium/appium-docker-android
**On step 8** replug Android devices. **NOTE:** You won’t be able to use the devices outside of the VM until the VM is stopped
**On step 10** the command to get IP address is wrong. Instead run `docker-machine ip appium-test-machine` in a new (not SSH) Terminal session.

**NOTE:** Commands operating on VBox machine should be issued via VM ssh session (Use `docker-machine ssh appium-test-machine` to start it)
**NOTE:** Commands operating on docker container should be issued with `docker-machine ssh appium-test-machine docker exec -t container-appium <command>`

### Switching between running tests locally or with Docker ###
Test run setup is configured with `TestSetup` Java property.
Accepted values:
* LOCAL_SETUP - run tests locally without Docker
* DOCKER_SETUP - run tests locally using Docker Appium container

**Changing IDEA TestNG run configuration to use Docker**
1. Open existing TestNG run configuration
2. On tab Configuration within subtab JDK settings add
  `-DTestSetup=DOCKER_SETUP` into **VM options** field