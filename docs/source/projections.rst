.. _boundless_android.projections:

========================
Projections
========================


Geodroid server utilizes the `proj4j`_ library for projection support.

Supported Coordinate Reference Systems
======================================

While the underlying proj4j library can handle many different coordinate
reference systems (crs), Geodroid server limits to those that are
represented by an `epsg`_ code. The entire list of supported coordinate
reference systems is defined `here`_.

A crs object is specified with the syntax is ``<authority>:<code>``.
Examples:

-  ``epsg:4326`` - "canonical" geographic based on WGS 84
-  ``epsg:900913`` - google/web mercator

.. _proj4j: http://trac.osgeo.org/proj4j/
.. _epsg: http://www.epsg.org/
.. _here: https://raw.github.com/jdeolive/proj4j/master/src/main/resources/nad/epsg
