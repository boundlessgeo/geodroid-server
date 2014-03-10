.. _boundless_android.boundless-android-server:

=============================
Boundless Server for Android
=============================


Introduction
============

Boundless Server for Android supports browser based mobile applications that require
bits of native functionality or must run in an offline setting with no
internet connection available.

It provides HTTP APIs that support:

.. cssclass:: styled

* Representing the geometry of spatial objects
* Positioning spatial objects in an arbitrary coordinate reference system and re-projecting objects between systems
* Reading and writing to and from common spatial data formats such as GeoJSON, MBTiles, and GeoPackage
* Extending the library with new and custom data formats
* Styling and rendering data for visualization on a map

The specific APIs are:

-  A :doc:`data-service` to discover published data on the device
-  A :doc:`feature-service` allowing for the reading and writing of vector data sets
-  A :doc:`tile-service` for serving up tile based data sets


.. note::

   The components in this toolkit are undergoing continuous development at this time and not final.


