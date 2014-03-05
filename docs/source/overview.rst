.. _boundless_android.overview:

============
Overview
============

The Boundless Android SDK is made up of two primary components. The first is the low level SDK itself. The second is the mobile server built on top of the SDK.

Lastly, a viewer in HTML and JavaScript runnable in any modern web browser is also included.

SDK
============

The SDK component provides the low level functionality for developers to build spatial applications. It itself is not a runnable app but a component that gets included in a runnable app.

The SDK consists for the most part of support for the following:

.. cssclass:: styled

* Geometries and projections
* Spatial formats
* Styling and rendering

At the moment the following spatial formats are supported:

.. cssclass:: styled

* GeoPackage
* ESRI Shapefile
* GeoGit
* GeoJSON
* MBTiles
* Comma Separated Value (CSV)


Server
============

The mobile server component is a runnable application that serves up data located on the device through HTTP accessible services. At the moment the following services are implemented:

.. cssclass:: styled

* Feature service for publishing vector data
* Map service for rendering vector data
* Tile service for publishing tile data

Along with the HTTP server is a management application that is used to control the server as well as browse through published data sets.
