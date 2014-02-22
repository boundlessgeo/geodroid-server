.. _boundless_android.installation:

============
Installation
============

**Components:**

`1. Boundless Android SDK`_
  Android library for building spatial applications.

`2. Boundless Android Server`_
  Embedded HTTP server providing simple Feature and Tile services.

`3. Boundless Android Viewer`_
  An OpenLayers Mobile viewer for maps served by Boundless Android Server



1. Boundless Android SDK
-------------------------------------

This is an Android library for building spatial applications.

Setting up the Android SDK
^^^^^^^^^^^^^^^^^^^^^^^^^^^

Building the library requires the `Android SDK`_.

After installing the SDK some additional packages must be installed through
the Android SDK Manager. Run the ``android`` command to start the SDK manager
and install the following packages:

* Android SDK Tools
* Android SDK SDK Platform-tools
* Android SDK Build-tools

And finally install the appropriate API package. Currently Boundless Android SDK is built
against "Android 4.0.3 (API 15)".

Download
^^^^^^^^^

`Boundless Android SDK`_

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


2. Boundless Android Server
------------------------------------------

Simple application providing an embedded HTTP server for serving up
GeoPackage and MBTiles packages through simple Feature and Tile
services.

Download
^^^^^^^^^

`Boundless Android SDK`_

Link the Boundless Android SDK library
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Start by doing a submodule update to bring in the core Boundless Android SDK library.

  .. code-block:: console

    git submodule update --init


Build the Boundless Android Server app
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Navigate back the root directory, update the project and build.

  .. code-block:: console

    cd ..
    android update project -p .
    mvn install

The above should result in a file named ``GeoDroidServer-debug.apk`` being created in the ``bin`` directory.


Installing
^^^^^^^^^^^

Install the app on a connected device with the ``adb`` command.

.. code-block:: console

  cd bin
  adb install GeoDroidServer-debug.apk

Running
^^^^^^^^^

After installation, a Boundless Android Server application will be available on your
device.

When started, there will be a slider to turn the Server on or off.

When online, there will be a symbol in the notification bar.

When tapped, a browser will open to serve data from ``/sdcard/www/`` on the
device storage at ``http://localhost:8000/www/``.



3. Boundless Android Viewer
-----------------------------
A viewer for maps served by Boundless Android Server.

Download
^^^^^^^^^^

`Boundless Android Viewer`_

Preparation
^^^^^^^^^^^^

Boundless Android Viewer needs `geodroid-server`_ installed and running on the
Android device.

To make use of Boundless Android Server, it is recommended to install a
GeoPackage on the device's SD-card. This can be done by unpacking the
contents of the http://dev.opengeo.org/~jdeolive/ne.zip GeoPackage to
``/sdcard/GeoData/`` on the Android device.

Installation
^^^^^^^^^^^^

To install the viewer, copy the contents of this folder to
``/sdcard/www/`` on your Android device. To make sure that everything
works, navigate to
http://localhost:8000/www/?layers=ne:tiles&features=ne:populated_places&center=48,16&zoom=4.

URL Parameters
^^^^^^^^^^^^^^

The content of the viewed map is controlled with URL parameters:

layers
  This parameter is used to pull in tile layers, and is mandatory. At
  least one layer needs to be specified, multiple tile layers can be
  provided as a comma separated list. Each tile layer consists of a prefix
  (e.g. 'ne' - the name of the GeoPackage that provides the layer, without
  the file extension) and a name (e.g. 'tiles' - the name of the layer in
  the GeoPackage), separated by a colon (':'). An optional file extension
  can also be appended (e.g. '.png').

    .. code-block:: console

        layers=<prefix>:<name>[.<extension>][,...]

features
  This optional parameter is used to pull in vector feature layers.
  Multiple feature layers can be provided as comma separated list. Each
  feature layer consists of a prefix (e.g. 'ne' - the name of the
  GeoPackage that provides the layer, without the file extension) and a
  name (e.g. 'populated\_places' - the name of the feature layer in the
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


.. _Android SDK: http://developer.android.com/sdk/index.html
.. _Apache Maven: http://maven.apache.org/
.. _Boundless Android SDK: http://github.com/boundlessgeo/geodroid/
.. _Boundless Android Server: http://github.com/boundlessgeo/geodroid-server/
.. _Boundless Android Viewer: http://github.com/ahocevar/geodroid-viewer/

