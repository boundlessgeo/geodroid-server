.. _tile-service:

============
Tile Service
============

The tile service provides access to tile data through an z, x, y protocol.

Get a tile
----------

::

    GET /tiles[/{workspace}]/{name}/{z}/{x}/{z}[.{format}]

Parameters ..........

-  **name**

*Required* ``string`` specifying the name of the tile dataset.

-  **z**

*Required* ``int`` specifying the z index (zoom) of the tile.

-  **x**

*Required* ``int`` specifying the x index (horizontal) of the tile.

-  **y**

*Required* ``int`` specifying the y index (vertical) of the tile.

-  **workspace**

*Optional* ``string`` specifying the name of the workspace containing
the tile dataset.

-  **format**

*Optional* ``string`` specifying format of which to return tile data.
Supported values include:

-  **png** - Tile image with ``Content-type: image/png``. This is the
   default.
-  **html** - OpenLayers map with ``Content-type: text/html``.

