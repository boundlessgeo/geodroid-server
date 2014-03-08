.. _boundless_android.view-shapefile:

========================
View Shapefiles
========================


Load a Shapefile
--------------------------

1. Upload the ``medford_parks`` shapefile to the ``/sdcard/Geodata`` directory on the device. (See :doc:`sample-data` for obtaining a sample file.)

2. Start the Boundless Server for Android app and ensure the server is online.

3. Navigate to the **Layers** menu. The table should contain a layer named “Medford_Parks” as shown in the figure below:

.. figure:: /img/shapefile_medford.png

   Server Layer Menu

4. Click on the map icon in the **Preview** column for the layer "Medford_Parks". This should result in the web browser showing an OpenLayers map rendering the Medford Parks data set.

.. figure:: /img/preview_medford.png

   Layer Preview: Medford Parks


Get Features
---------------

This section describes basic operation of the feature service of returning all features.

1.  In a web browser navigate to the the url: http://localhost:8000/features/Medford_Parks/Medford_Parks.json

2.  The result should be a GeoJSON dump of all 88 features of the Medford Parks data set.

**Get Feature by id**

Fetch a single feature using its identifier.

1.  Obtain the “id” attribute of an individual feature object in the GeoJSON feature collection from the get features request. The value “0” will be used here but any id in the collection will work.

2.  In a web browser navigate to the url:
http://localhost:8000/features/Medford_Parks/Medford_Parks/0.json

3.  The result should be the GeoJSON representation of feature with id 0, in this case Bear Creek Channel.

.. figure:: /img/getfeature_medford.png

   Feature id in red


Reprojecting Features
----------------------

The feature service is able to re-project features to a specified coordinate system, in this case Spherical Mercator.

More information regarding supported projections can be found in the section on :doc:`projections`.

1. In a web browser navigate to the url: http://localhost:8000/features/Medford_Parks/Medford_Parks.json?srs=epsg:900913

2. The result should be a GeoJSON dump of all features with coordinates displayed in the Spherical Mercator projection.


Filtering Features by Bounding Box
-----------------------------------

The feature service can filter features by spatial extent (i.e. bounding box).

1. In a web browser navigate to the url: http://localhost8000/features/Medford_Parks/Medford_Parks.json?bbox=4292578.94,215486.8,4311097.39,277058.63

2. The result should be a limited set of 7 features representing ‘Agate Lake and Dam’ and ‘Prescott Park’.

.. figure:: /img/bbox_medford.png

    Bounding Box Filter


Filtering Features by Attribute Filter
----------------------------------------

The feature service can filter features by attribute filter specified as Common Query Language (CQL).

1.  In a web browser navigate to the url:

.. code:: console

    http://localhost:8000/features/Medford_Parks/Medford_Parks.json?filter=NAME+EQ+'Roosevelt School'

2.  The result should be a single feature representing the Medford park named Roosevelt School.

.. figure:: /img/cql_medford.png

     Result for an Attribute Filter for NAME = 'Roosevelt School'
