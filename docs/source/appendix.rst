.. _boundless_android.appendix:

========================
Appendix
========================

Below are some additional tools and notes that may help with installation.

`Installing Software on the Device`_

`Getting Data on the Device`_

`File Transfer Tools`_

`Updating your PATH`_

`Working with Zip Files`_

`Obtaining the Device IP address`_


Installing Software on the Device
======================================

Installation requires putting files on the device. While is possible to achieve this with the Android SDK Tools it is often more convenient to use another file transfer tool or option depending on the "host" operating system.

**Option A:** Copy .apk to the device

1. Copy the GeodroidServer.apk file to the device (on the SDCard) using the file transfer tool of your choice. See the `File Transfer Tools`_ section below for information about file transfer tools.

2. Using the File Manager app, or alternatively use the Chrome browser to navigate to the apk path using a location of ``//sdcard/GeodroidServer.apk`` in the address bar.

3. Open the file. If the installation is blocked (which will result in a dialog) adjust setting to allow “Unknown sources”.

4. The installation should proceed.


**Option B:** Email the file to the device

1. Email an account accessible on the device with the GeodroidServer.apk file attached.

2. Once the email is on the device, click the attachment.

3. See step A.3 above if the installation is blocked.

4. The installation should proceed.


**Option C:** Use the SDK tools

1. Attach the device to the desktop via the USB cable.

2. Verify connectivity by running the adb command (this should display a listing of the device):

.. code-block:: console

  adb devices -l

If no device is detected, ensure the ‘USB debugging’ option is selected in Settings > Developer options. If working on an Android 4.2+ device developer settings may hidden. See the following for information about how to make them available:

http://www.androidcentral.com/how-enable-developer-settings-android-42

3.  Install the software by running the adb command:

.. code-block:: console

  adb install GeodroidServer-debug.apk

You may be prompted about verifying the software.

4.  The installation should proceed.


.. _getting-data-on-the-device:

Getting Data on the Device
===============================

**Option A:** Use a file transfer tool of your choice (see :ref:`file_transfer` below) to transfer files

Copy the files to the ``Geodata`` directory (this may also appear as ``/sdcard/Geodata`` depending upon the tool).


**Option B:** Email the data to the device

1.  Download the attachments. They will be saved in the ``Download`` directory on the device.

2.  Using the File Manager App, copy the file to the ``Geodata`` directory.


**Option C:** Use the SDK tools

Using the ``adb`` tool, run the following command for each data file:

.. code-block:: console

  adb push <data_file> /sdcard/Geodata

After copying the data to the device, the new data should appear in the ‘Layer Preview’ view. If it does not appear immediately, return to the main view and then back to the ‘Layer Preview’.


.. _file_transfer:

File Transfer Tools
=====================

**Windows**

It should be possible to connect directly to the device over USB without any additional software.

**Mac OS X**

Mac users can install the Android File Transfer tool located at http://www.android.com/filetransfer/.

**Linux**

Depending on the distribution additional configuration may be required.

.. cssclass:: styled

* **gmtp** - http://gmtp.sourceforge.net/
* **mtpfs** - http://www.adebenham.com/mtpfs/


**Wireless Connectivity** (Optional)

It is useful to be able to connect to the mobile server in the web browser on the host machine rather than use the web browser on the device.


**Android SDK Tools** (Optional)

The adb tool that comes with the Android SDK tools is useful to install software and upload files to the device. See http://developer.android.com/sdk/ for download and installation instructions.


**Android File Manager App** (Optional)

Useful for browsing data on the device. The app can be installed from the Google Play store: https://play.google.com/store/apps/details?id=com.rhmsoft.fm.


**Chrome JSONView Plugin** (Optional)

Many of the mobile server tests involve making requests that return JSON output. This plugin formats JSON in the browser making it more readable.

The plugin can be installed from the Chrome web store: https://chrome.google.com/webstore/detail/jsonview/chklaanhfefbnpoihckbnefhakgolnmc?hl=en.




Working with Zip Files
========================

Some of the tests below require a zip archive to be extracted on the device. Most Android file managers don’t come in with built in zip handling but there are a number of applications that provide this functionality.

One such application is AndroZip, available from the Google Play store:
https://play.google.com/store/apps/details?id=com.agilesoftresource


.. _obtaining-the-device-ip-address:

Obtaining the Device IP address
===================================

If you plan to execute tests from a host desktop connecting to the device over a wireless network then the IP address of the device will be need. While the exact steps can change based on Android version generally the IP address can be obtained from the Wireless Settings.

1. Open the Settings app

2. Find the wireless settings and the list of available wireless networks.

3. Click on the network that the device is currently connected to.

4. The device IP should be shown.


.. _updating_path:

Updating your `PATH`
======================

To update the ``PATH`` on Windows see the following:

http://superuser.com/questions/317631/setting-path-in-windows-7-command-prompt

To update the PATH on Linux or OSX issue the following command:

.. code-block:: console

  % export PATH=`pwd`/geogit/bin:$PATH

