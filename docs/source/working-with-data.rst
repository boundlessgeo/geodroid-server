.. _boundless_android.working-with-data:

========================
Working with Data
========================

This page describes how to configure Geodroid server to serve spatial
data.

Supported Formats
-----------------

-  `GeoJSON`_
-  `MBTiles`_
-  `GeoPackage`_
-  `CSV`_
-  `Shapefile`_
-  `GeoGit`_

Data Directory
--------------

Geodroid server serves up spatial data that resides physically on the
device, usually on the SD card. A single directory, known as the *data
directory* is used to store data. By default this directory is located
at ``/mnt/sdcard/Geodata``.

Data Configuration
------------------

Geodroid server supports two methods of configuration data.

Discovery Mode
~~~~~~~~~~~~~~

The first is known as *discovery mode*. In this mode the server will
scan the contents of the data directory for files in spatial formats
that it recognizes. Adding data to the server involves simply copying
the data into the data directory.

Index Mode
~~~~~~~~~~

The second mode of data configuration is known as *index mode*. In this
mode the server looks for a file named ``index.json`` located in the
data directory. The index file contains a list of all data sources that
the server should publish. Adding data to the server involves editing
the ``index.json`` file.

The index file consists of a single JSON object whose keys whose keys
are names of the data objects. Each key maps to an object that contains
the following properties.

-  driver - the name or alias identifying the driver for the data
-  keys - object containing the key / values pairs defining the data
   connection options

The following is an example consisting of a single GeoJSON dataset in a
file named ``states.json`` in the same directory as the index file.

::

    {
      "states": {
         "driver": "geojson",
         "keys": {
            "file": "states.json"
         }
      }
    }

The following is a more comprehensive example. In this example we use
the shorthand of using a ``file`` property on the data object, rather
than specify it within the keys.

::

    {
      "states": {
         "driver": "geojson",
         "file": "states.json"
      },
      "va": {
         "driver": "gpkg",
         "file": "va.gpkg"
      },
      "ne1": {
         "driver": "gpkg",
         "file": "va.gpkg"
      },
      "countries": {
         "driver": "mbtiles",
         "file": "countries.mbtiles"
      },
      "style": {
         "driver": "css",
         "file": "style.css"
      }
    }

The above specifies the following data objects:

-  A GeoJSON ``states.json`` file
-  Two GeoPackage files named ``va.gpkg`` and ``ne1.gpkg``
-  A MBTiles file named ``countries.mbtiles``
-  A CSS style file named ``style.css``

Data Endpoint
-------------

The :doc:`data-service` can be used to validate the server data
configuration. With the server running visit the url
``http://localhost:8000/data``.

MBTiles Support
---------------

Only the tile component of MBTiles is supported. UTFGrid support is not
implemented at this time.

GeoPackage Support
----------------------

The feature and tile components of the GeoPackage specification are
supported. Raster support is not yet implemented at this time. At the
time of the writing of this document the GeoPackage spec is not yet
finalized.


.. _GeoJSON: http://geojson.org
.. _MBTiles: https://www.mapbox.com/developers/mbtiles/
.. _GeoPackage: https://github.com/opengis/geopackage
.. _CSV: http://en.wikipedia.org/wiki/Comma-separated_values
.. _Shapefile: http://en.wikipedia.org/wiki/Shapefile
.. _GeoGit: http://geogit.org

