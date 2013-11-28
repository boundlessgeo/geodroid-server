.. _data-service:

Data Service
============

The data service provides information about data that the server is publishing.

See :ref:`working-with-data` for information about data configuration.

List all data objects
---------------------

    GET /data

Response
~~~~~~~~

::

  HTTP/1.0 200 OK
  Content-Type: application/json

::

    {
        "states": {
            "type": "workspace",
            "driver": "GeoJSON"
        },
        "va": {
            "type": "workspace",
            "driver": "GeoPackage"
        },
        "ne1": {
            "type": "workspace",
            "driver": "GeoPackage"
        },
        "geography": {
            "type": "workspace",
            "driver": "MBTiles"
        },
        "gun_deaths": {
            "type": "workspace",
            "driver": "CSV"
        },
        "places": {
            "type": "style",
            "driver": "CartoCSS"
        },
        "ne": {
            "type": "workspace",
            "driver": "GeoPackage"
        },
        "style": {
            "type": "style",
            "driver": "CartoCSS"
        },
        "air_runways": {
            "type": "workspace",
            "driver": "GeoJSON"
        },
        "roads": {
            "type": "style",
            "driver": "CartoCSS"
        },
        "parks": {
            "type": "style",
            "driver": "CartoCSS"
        }
    }

Get a workspace
---------------

    GET /data/{workspace}

Parameters
~~~~~~~~~~

* **workspace**

  *Required* ``string`` specifying the name of the workspace.

Response
~~~~~~~~

::

  HTTP/1.0 200 OK
  Content-Type: application/json

::

    {
        "type": "workspace",
        "driver": "GeoPackage",
        "datasets": [
            "admin_0_countries",
            "admin_0_boundary_lines_land",
            "admin_1_states_provinces_lines_shp",
            "parks_and_protected_lands_scale_rank",
            "lakes_north_america",
            "urban_areas",
            "populated_places",
            "roads_north_america"
        ]
    }

Errors
~~~~~~

No such object with specified name exists.

    HTTP/1.0 404 Not Found

Get a dataset
-------------

    GET /data/{workspace}/{dataset}

Parameters
~~~~~~~~~~

* **workspace**

  *Required* ``string`` specifying the name of the workspace.

* **workspace**

  *Required* ``dataset`` specifying the name of the dataset.


Vector Response
~~~~~~~~~~~~~~~

::

  HTTP/1.0 200 OK
  Content-Type: application/json

::

    {
      "name": "states",
      "type": "vector",
      "driver": "GeoJSON",
      "bbox": [
        -124.731422,
        24.955967,
        -66.969849,
        49.371735
      ],
      "crs": [
        "+proj=longlat",
        "+datum=WGS84",
        "+no_defs"
      ],
      "count": 49,
      "schema": {
        "STATE_NAME": "String",
        "STATE_ABBR": "String",
        "PERSONS": "Double",
        "geometry": "Polygon"
      },
      "features": "/features/states.json"
    }

Tileset Object Response
```````````````````````

::

  HTTP/1.0 200 OK
  Content-Type: application/json

::

    {
      "name": "tiles",
      "type": "tile",
      "driver": "GeoPackage",
      "bbox": [
        -180.0,
        -90.0,
        180.0,
        90.0
      ],
      "crs": [
        "+proj=longlat",
        "+datum=WGS84",
        "+no_defs"
      ],
      "tilesize": [
        256,
        256
      ],
      "grids": [
        {
          "zoom": 0,
          "width": 2,
          "height": 1,
          "res": [
            0.703125,
            0.703125
          ]
        },
        {
          "zoom": 1,
          "width": 4,
          "height": 2,
          "res": [
            0.3515625,
            0.3515625
          ]
        }
      ],
      "tiles": "/tiles/ne1/tiles"
    }


Errors
~~~~~~

No such object with specified name exists.

    HTTP/1.0 404 Not Found
