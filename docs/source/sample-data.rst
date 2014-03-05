.. _boundless_android.adding-data:

========================
Sample Data
========================

This page describes how to install some additional reference data sets for trying out the Boundless Android SDK.

Download and unpack the contents of the following to ``/sdcard/GeoData/`` directory on the Android device.

**GeoPackage**
     http://data.boundlessgeo.com/mobile/ne.zip  (if not already installed)
**Shapefile**
     http://data.boundlessgeo.com/mobile/medford_parks.zip
**GeoGit**
     http://data.boundlessgeo.com/mobile/world.zip

Natural Earth
-----------------

This data set contains a GeoPackage file containing data from the well known `Natural Earth`_ collection.

The file ``va.gpkg`` consists of a subset of data centered around the state of Virginia, USA. It contains the following cultural and physical layers at 1:10m scale:

.. admonition:: Natural Earth

  * Countries - Countries of the world
     http://www.naturalearthdata.com/downloads/10m-cultural-vectors/10m-admin-0-countries/
  * Country boundaries - Country boundaries on land and offshore
     http://www.naturalearthdata.com/downloads/10m-cultural-vectors/10m-admin-0-boundary-lines/
  * State boundaries - Internal administrative boundaries
     http://www.naturalearthdata.com/downloads/10m-cultural-vectors/10m-admin-1-states-provinces/
  * Roads - Transportation
     http://www.naturalearthdata.com/downloads/10m-cultural-vectors/roads/
  * Urban areas - Areas of dense human habitation
     http://www.naturalearthdata.com/downloads/10m-cultural-vectors/10m-urban-area/
  * Parks - National parks
     http://www.naturalearthdata.com/downloads/10m-cultural-vectors/parks-and-protected-lands/
  * Populated Places - City and town points
     http://www.naturalearthdata.com/downloads/10m-cultural-vectors/10m-populated-places/
  * Lakes - Natural and artificial lakes
     http://www.naturalearthdata.com/downloads/10m-physical-vectors/10m-lakes/



Medford
-----------------

The Shapefile comprising of parks data from the city of Medford, Oregon, USA. The zip file ``medford_parks.zip`` contains all the components of the Shapefile including the ``.shp``, ``.shx``, ``.dbf``, and ``.prj`` files.


World Cities
------------------
The World Cities data contains a GeoGit repository containing North American cities. The dataset is derived from `Wikipedia's list`_ of the 20 most populous cities in North America.

The ``world.zip`` file contains a directory named ``world`` and a file named ``world.jeo`` located parallel to the directory. Both files are required for the mobile server to recognize the repository.


.. _Natural Earth: http://www.naturalearthdata.com
.. _Wikipedia's list: http://en.wikipedia.org/wiki/List_of_North_American_cities_by_population

