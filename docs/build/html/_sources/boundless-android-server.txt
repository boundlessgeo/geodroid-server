.. _boundless-android-server:

========================
Boundless Android Server
========================


Introduction
============

Boundless Android Server supports browser based mobile applications that require
bits of native functionality or must run in an offline setting with no
internet connection available.

It provides HTTP APIs that support:

-  Representing the geometry of spatial objects
-  Positioning spatial objects in an arbitrary coordinate reference
   system and re-projecting objects between systems
-  Reading and writing to and from common spatial data formats such as
   GeoJSON, MBTiles, and GeoPackage
-  Extending the library with new and custom data formats
-  Styling and rendering data for visualization on a map

The specific APIs are:

-  A :ref:``Data Service`` to discover published data on the device
-  A :ref:``Tile Service`` for serving up tile based data sets
-  A :ref:``Feature Service`` allowing for the reading and writing of
   vector data sets



.. _Data Service: data-service
.. _Feature Service: feature-service
.. _Tile Service: tile-service

