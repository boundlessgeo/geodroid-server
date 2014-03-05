.. _boundless_android.view-geogit:

======================================
View and Edit GeoGit Repositories
======================================

The mobile server can read and write data to and from a GeoGit repository.

The power of GeoGit is in the ability to synchronize with a remote repository. In mobile contexts a common workflow is to:

.. cssclass:: styled

* download (pull) a subset of a repository from a remote server,
* make modifications to data locally, and then
* upload (push) those changes back to the master copy on the remote server.

The GeoGit project supports a network protocol that achieves this goal. For more info see http://geogit.org.


Create, Load and Edit a GeoGit Repository
---------------------------------------------

The general workflow for this section is as follows:

* `1. Install GeoGit Command Line Tools`_
* `2. Create a New GeoGit Repository`_
* `3. Synchronize the Repository with the Server`_
* `4. Clone the Repository to the Device`_
* `5. Make Edits on the Device`_
* `6. Synchronize Changes from the Device with the Server`_


1. Install GeoGit Command Line Tools
-------------------------------------

To work with GeoGit repositories, install the GeoGit CLI (Command Line Interface) tools from http://geogit.org.

1. Unpack the ``geogit-cli.zip`` file from the test bundle onto the desktop.

2. Update the command ``PATH`` so that it includes the bin directory from the cli archive. See :ref:`updating_path` for more info.

3.  Verify the CLI tools are properly installed by issuing the the following command from a command prompt.

.. code-block:: console

    % geogit version


2. Create a New GeoGit Repository
----------------------------------

In this section a new GeoGit repository will be created that will serve as the basis for testing in the following sections.

1. Using the CLI create a new GeoGit repository with the following command:

.. code-block:: console

    % geogit init world --config storage.objects=sqlite,storage.staging=sqlite,storage.graph=sqlite,sqlite.version=0.1

2. Unzip the cities.zip file from the test bundle. It contains a Shapefile to be imported into the new GeoGit repository.

3. Import the shapefile with the following command:

.. code-block:: console

    % cd world
    % geogit shp import ../cities.shp

  The output should look similar to the following:

.. code-block:: console

    Importing from shapefile ../cities.shp
    Importing cities           (1/1)...
    5%
    20 distinct features inserted in 83.42 ms

    Building final tree...

    20 features tree built in 3.064 ms
    100%
    ../cities.shp imported successfully.

4. Next stage and with the following command:

.. code-block:: console

    % geogit add cities
    Counting unstaged elements...20
    Staging changes…
    105
    20 features and 1 trees staged for commit
    0 features and 0 trees not staged for commit

5. And finally commit:

.. code-block:: console

    % geogit commit -m “Added cities, initial commit”
    100%
    [...] Added cities,  initial commit
    Committed, counting objects...20 features added, 0 changed, 0  deleted.

6. To verify the import was successful use the log command:

.. code-block:: console

    % geogit log

The result should look something like the following:

.. code-block:: console

    % geogit log
    Commit:  49511dd5ff447b2980ad322dab5bfb62d7c6feab
    Author:  jdeolive <jdeolive@boundlessgeo.com>
    Date:    (6 minutes ago) 2014-03-03 13:29:29 -0700
    Subject: Added cities, initial commit



3. Synchronize the Repository with the Server
-----------------------------------------------

Before the new GeoGit repository is uploaded to the device it will first be synchronized with a server running locally on the desktop.

1. In a separate directory create a new empty repository named “world”:

.. code-block:: console

  % geogit init world

2. Serve up the repository using the serve command:

.. code-block:: console

  % geogit serve world
  Starting server on port 8182, use CTRL+C to exit.

3. Change directory back to the first repository created in the previous section and create a new remote called “origin” that points to the running server:

.. code-block:: console

    % cd world
    % geogit remote add origin http://192.168.1.101:8182

.. admonition:: Note

    Replace “192.168.1.101” with the IP address of the local machine. This address must be visible from the mobile device so do not use “localhost”.

4.  Synchronize with the server:

.. code-block:: console

    % geogit push origin master
    % geogit pull origin master

5.  To verify the changes were synchronized:

.. cssclass:: styled

    - Shut down the server
    - Change directory back to the new repository created in this section
    - Use the geogit log command to verify that the same commits are present

6.  Once again start the server on the new repository.



4. Clone the Repository to the Device
---------------------------------------

The next step is to upload the GeoGit repository to the device.

1.  Upload the first GeoGit repository created in the previous sections to the `/sdcard/Geodata` directory on the device. See :ref:`getting-data-on-the-device` for details on how to upload data.

2.  On the device created a file named `world.jeo` located next to the world repository with the following contents:

.. code-block:: console

    {
      "driver": "geogit",
      "options": {
        "file": "world"
      }
    }

3.  Once completed the file structure on the device should look like the following:

.. code-block:: console

    /sdcard/Geodata/world/
    /sdcard/Geodata/world.jeo

4.  To verify the mobile server can read the GeoGit repository visit http://localhost:8000/features/world/cities.json in the web browser. The result should be an OpenLayers map containing the 20 point features from the cities dataset.

.. figure:: /img/geogit_openlayers.png

    GeoGit repository in OpenLayers


**Upload the Sample App**

A sample OpenLayers-based application will be used to facilitate editing in the next few sections. The first step is to upload the app to the “apps” directory on the device.

1.  Upload the **geogitapp.zip** file from http://data.boundlessgeo.com/mobile/geogitapp.zip to the ``/sdcard/Geodroid/apps`` directory on the device.

2.  Unpack the zip file. Upon success the directory ``/sdcard/Geodroid/apps/geogit`` should exist.

3.  Visit http://localhost:8000/apps/geogit/ in the web browser. The result should be a simple OpenLayers map with some editing tools.

.. figure:: /img/geogit_openlayers_edit.png

    OpenLayers with Editing Tools


5. Make Edits on the Device
------------------------------

**Add a Feature**

Add a new feature to the Geogit repository.

1.  Click on the **Add Feature** tool and then click anywhere on the map to add a new feature.

.. figure:: /img/geogit_add_feature_tool.png

      Add Feature tool

2.  Once the new feature has been added and is highlighted in blue use the **Name** text field on the upper right to specify a name for the new feature.

3.  Optionally specify a **Commit message** and **Author** using the form components located below the **Name** text field.

4.  Once completed click the **Save** button to save the new feature back to the server.

5.  To verify the new feature has been added and persisted on the server reload the web page and verify that the newly added feature is still there.

.. figure:: /img/geogit_openlayers_add_feature.png

    Add a Feature


**Edit a Feature**

Change a feature in a Geogit repository.

1. Click on the **Edit Feature** tool and then click on a feature to edit. The feature will be highlighted in blue.

.. figure:: /img/geogit_edit_feature_tool.png

    Edit Feature tool

2. Change the location of the feature by dragging it on the map and/or change the feature name with the **Name** text field.

3. Once completed specify a **Commit message** click the **Save** button to save the new feature back to the server.

.. figure:: /img/geogit_openlayers_edit_feature.png

    Edit a Feature

4. Refresh the page to verify the changes are persisted.


**Delete a Feature**

Remove a feature from a GeoGit repository.

1. Click on the **Delete Feature** tool and then click on a feature to delete.

.. figure:: /img/geogit_openlayers_delete_feature.png

      Delete Feature tool

2. Enter a **Commit message** and click the **Save** button.

3. Refresh the page to verify that the feature has been removed.

.. figure:: /img/geogit_openlayers_delete.png

    Edit a Feature



6. Synchronize Changes from the Device with the Server
-------------------------------------------------------

Push changes to the server from the device.

1. Start the Geodroid Server app.

2. Navigate to the **GeoGit** menu. The "world" repository should be in the list.

.. figure:: /img/geogit_world_repo.png

    world GeoGit repo

3. Under the **Sync** column click the **Refresh** button.

.. figure:: /img/geogit_refresh_button.png

    Refresh button

During synchronization the button will change to a spinner icon. Once completed the icon will change back to the refresh button.

4. Back on the desktop shut down the server that was started with the ``geogit serve`` command.

5.  Change directory to the repository that the server was running from and run the ``geogit log`` command. The log should contain the history of edits made with the edit app.

.. code-block:: console

      % geogit log
      Commit:  176dfd4504f6facb2a0083fc950054c782bfaa7d
      Author:  jdeolive
      Date:    (4 minutes ago) 2014-03-03 17:40:39 -0700
      Subject: Removing Vancouver.

      Commit:  91359ac336da259985f980215562106253f3809f
      Author:  jdeolive
      Date:    (9 minutes ago) 2014-03-03 17:35:18 -0700
      Subject: Putting Calgary in the right place.

      Commit:  577da4712175f5bb6d933cf3343eb1a68c23cf6f
      Author:  jdeolive
      Date:    (10 minutes ago) 2014-03-03 17:34:32 -0700
      Subject: Added city of Calgary.

      Commit:  35acd66d36573e0ae6a0886733ef6bfc54e6377d
      Author:  jdeolive <jdeolive@boundlessgeo.com>
      Date:    (12 minutes ago) 2014-03-03 17:36:42 -0700
      Subject: Added cities, initial commit



