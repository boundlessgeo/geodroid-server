.. _boundless_android.installation:

============
Installation
============

**Components**

`1. Boundless SDK for Android`_
  Android library for building spatial applications.

`2. Boundless Server for Android`_
  Embedded HTTP server providing simple Feature and Tile services.

`3. Boundless Viewer for Android`_
  An OpenLayers Mobile viewer for maps served by Boundless Server for Android



1. Boundless SDK for Android
-------------------------------------

This is an Android library for building spatial applications.

Setting up the SDK
^^^^^^^^^^^^^^^^^^^

Building the library requires the `Android SDK`_.

After installing the SDK some additional packages must be installed through
the Android SDK Manager. Run the ``android`` command to start the SDK manager
and install the following packages:

.. cssclass:: styled

* Android SDK Tools
* Android SDK SDK Platform-tools
* Android SDK Build-tools

And finally install the appropriate API package. Currently Boundless SDK for Android is built
against "Android 4.0.3 (API 15)". These tools have also been tested on recent versions of the Android platform including Ice Cream Sandwich (4.0) through KitKat (4.4).

Download
^^^^^^^^^

`Boundless SDK for Android`_

Building
^^^^^^^^^

Building the library from sources requires `Apache Maven`_.

Once the Android SDK is set up and Maven is installed run the following command
in the root of the project.

.. code-block:: console

    android update project -p .

And finally run Maven to build the library.

.. code-block:: console

   mvn install

.. note:: On OSX an error may occur during Maven execution that looks like:

  .. code-block:: console

    [ERROR] com.sun.tools.javac.Main is not on the classpath.
    [ERROR] Perhaps JAVA_HOME does not point to the JDK.

  In that case try executing Maven with the ``tools.jar`` profile:

  .. code-block:: console

    mvn -P tools.jar install


2. Boundless Server for Android
------------------------------------------

Simple application providing an embedded HTTP server for serving up
GeoPackage and MBTiles packages through simple Feature and Tile
services.

Download
^^^^^^^^^

`Boundless SDK for Android`_

Link the Boundless SDK for Android library
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Start by doing a submodule update to bring in the core Boundless SDK for Android library.

.. code-block:: console

    git submodule update --init


Build the Boundless Server for Android app
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Navigate back the root directory, update the project and build.

.. code-block:: console

    cd ..
    android update project -p .
    mvn install

The above should result in a file named ``GeoDroidServer-debug.apk`` being created in the ``bin`` directory.


Installing
^^^^^^^^^^^

Install the app on a connected device (e.g. a deviceconnected via USB to a host machine that contains the ``.apk``) by using the ``adb`` command that comes with the Android SDK tools. [See the :doc:`appendix` for more tools and other installation options.]

.. code-block:: console

  cd bin
  adb install GeoDroidServer-debug.apk


Running
^^^^^^^^^

After installation, a Boundless Server for Android application will be available on your
device. Locate the application icon on the home screen:

.. figure:: /img/icon.png

   Boundless Server for Android icon

When started, there will be a slider to turn the Server on or of in the upper right-hand corner of the screen. Toggle the "OFFLINE" button, upon successful start the button should change to green and read  to "ONLINE".


Verify Connectivity
^^^^^^^^^^^^^^^^^^^^

Verify connectivity on the device by using Chrome to navigate to the URL http://localhost:8000. The result should be the following web page:

.. figure:: /img/localhost.png

   Boundless Server for Android homepage

When tapped, a browser will open to serve data from ``/sdcard/www/`` on the
device storage at ``http://localhost:8000/www/``.

Optionally verify connectivity from the desktop:

.. cssclass:: styled

* Obtain the IP address of the device on the local network (if the device has wireless capabilities and is connected to the local network - also see :ref:`obtaining-the-device-ip-address`)
* In the desktop browser, visit ``http://<IP Adress>:8000/``
* The same web page as shown above should be visible.


Configuring the Server Port
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To configure a different port on the device for serving data, navigate to the "Settings" menu of the Server app and select the "Port" option. Change the port the service is running on.

.. figure:: /img/serverport.png

   Configuring the Server's port



3. Boundless Viewer for Android
----------------------------------
A viewer for maps served by Boundless Server for Android.

Download
^^^^^^^^^^

`Boundless Viewer for Android`_

Preparation
^^^^^^^^^^^^

Boundless Viewer for Android needs `Boundless Server for Android`_ installed and running on the Android device.

To make use of Boundless Server for Android, it is recommended to install a
GeoPackage on the device's SD card. This can be done by unpacking the
contents of the http://data.boundlessgeo.com/mobile/ne.zip GeoPackage to
``/sdcard/GeoData/`` on the Android device.


Installation
^^^^^^^^^^^^

To install the viewer, copy the contents of this folder to
``/sdcard/www/`` on your Android device. To make sure that everything
works, navigate to http://localhost:8000/www/?layers=ne:tiles&features=ne:populated_places&center=48,16&zoom=4.

URL Parameters
^^^^^^^^^^^^^^

The content of the viewed map is controlled with URL parameters:

layers
  This parameter is used to pull in tile layers, and is mandatory. At
  least one layer needs to be specified, multiple tile layers can be
  provided as a comma separated list. Each tile layer consists of a prefix
  (e.g. *ne* - the name of the GeoPackage that provides the layer, without
  the file extension) and a name (e.g. *tiles* - the name of the layer in
  the GeoPackage), separated by a colon ( **':'** ). An optional file extension
  can also be appended (e.g. *.png*).

    .. code-block:: console

        layers=<prefix>:<name>[.<extension>][,...]

features
  This optional parameter is used to pull in vector feature layers.
  Multiple feature layers can be provided as comma separated list. Each
  feature layer consists of a prefix (e.g. *ne* - the name of the
  GeoPackage that provides the layer, without the file extension) and a
  name (e.g. *populated\_places* - the name of the feature layer in the
  GeoPackage).

  .. code-block:: console

          features=<prefix>:<name>[,...]

center
  This optional parameter is used to specify the initial center of the
  map.

  .. code-block:: console

        center=<latitude>,<longitude>

zoom
  This optional parameter is used to specify the initial zoom level of the
  map.

  .. code-block:: console

        zoom=<zoomlevel>


GeoGit Viewer
^^^^^^^^^^^^^^

Another sample viewer application that can be used with a GeoGit repository is available in the :doc:`view-geogit` section.


.. _Android SDK: http://developer.android.com/sdk/index.html
.. _Apache Maven: http://maven.apache.org/
.. _Boundless SDK for Android: http://github.com/boundlessgeo/geodroid/
.. _Boundless Server for Android: http://github.com/boundlessgeo/geodroid-server/
.. _Boundless Viewer for Android: http://github.com/ahocevar/geodroid-viewer/

