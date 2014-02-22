Introduction
============

GeoDroid Server supports browser based mobile applications that require bits of native functionality or must run in an offline setting with no internet connection available.

It provides HTTP APIs that support:

* Representing the geometry of spatial objects
* Positioning spatial objects in an arbitrary coordinate reference system and re-projecting objects between systems
* Reading and writing to and from common spatial data formats such as GeoJSON, MBTiles, and GeoPackage
* Extending the library with new and custom data formats
* Styling and rendering data for visualization on a map

The specific APIs are:

* A :ref:`data-service` to discover published data on the device
* A :ref:`tile-service` for serving up tile based data sets
* A :ref:`feature-service` allowing for the reading and writing of vector data sets

