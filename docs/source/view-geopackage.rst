.. _boundless_android.view-geopackage:

========================
View GeoPackages
========================


Load a GeoPackage
--------------------------

1.  Upload the ``va.gpkg`` file to the ``/sdcard/Geodata`` directory on the device. (See :doc:`sample-data` for obtaining a sample file.)

2.  Start the Geodroid Server app and ensure the server is online.

3.  Navigate to the Layers menu. The table should contain a number of entries corresponding to the data sets in the geopackage.

.. figure:: /img/geopackage_layers.png

    Server Layer Menu

4. Click on the map icon in the **Preview** column for the layer "Populated Places". This should result in the web browser showing an OpenLayers map rendering the data set.

.. figure:: /img/geopackage_preview.png

    Layer Preview for the "Populated Places" layer



Get Features
---------------

This section describes basic operation of the feature service of returning all features.

1. In a web browser navigate to the the url: http://localhost:8000/features/va/populated_places.json

2. The result should be a GeoJSON dump of all 15 features of the Virginia Populated Places data set.

**Get Feature by id**

Fetch a single feature using its identifier.

1. Obtain the “id” attribute of an individual feature object in the GeoJSON feature collection from the get features request. The value "4" will be used here but any id in the collection will work.

2. In a web browser navigate to the url: http://localhost:8000/features/va/populated_places/4

3. The result should be the GeoJSON representation of feature with id 4, in this case Hagerstown.

.. figure:: /img/geopackage_getfeature.png

    Feature id in red


Reprojecting Features
----------------------

The feature service is able to re-project features to a specified coordinate system, in this case Spherical Mercator.

More information regarding supported projections can be found in the section on :doc:`projections`.

1. In a web browser navigate to the url: http://localhost:8000/features/va/populated_places.json?srs=epsg:900913

2. The result should be a GeoJSON dump of all features with coordinates displayed in the Spherical Mercator projection.


Filtering Features by Bounding Box
-----------------------------------

The feature service can filter features by spatial extent (i.e. bounding box).

1. In a web browser navigate to the url: http://localhost8000/features/va/populated_places.json?bbox=81.2518834,37.7381591,81.2518832,37.7381610

2. The result should be a limited set of cities in Virginia.


Filtering Features by Attribute Filter
----------------------------------------

The feature service can filter features by attribute filter specified as Common Query Language (CQL).

1.  In a web browser navigate to the url:
http://localhost:8000/features/va/populated_places.json?filter=NAME+EQ+'Dover'

2.  The result should be a single feature representing Dover.

.. figure:: /img/geopackage_filter.png

  Result for an Attribute Filter for NAME = 'Dover'


Tile Service
----------------------------------------

The tile service delivers tiles using an XYZ protocol.

1. Upload the ``ne1.gpkg`` file from the test bundle to the ``/sdcard/Geodata`` directory on the device.

2. In a web browser navigate to the the url: http://localhost:8000/tiles/ne1/tiles.png

3. The result should be a png tile that looks like the following:

.. figure:: /img/geopackage_tileservice.png

    Tile example

4. Navigate to the the url: http://localhost:8000/tiles/ne1/tiles/0/1/0.png

5. The result should be a png image that looks like the following:

.. figure:: /img/geopackage_tileservice2.png

    Another Tile example

6. Navigate to the the url: http://localhost:8000/tiles/ne1/tiles.html

7. The result should be an OpenLayers map consisting of the tile layer.

.. figure:: /img/geopackage_openlayers.png

    OpenLayers tile service request


