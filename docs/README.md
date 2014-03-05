geodroid-docsite
================

Boundless Android SDK docs website in Sphinx

Live site is located at

   http://boundlessmobile.github.io 

(See http://github.com/boundlessgeo/boundlessmobile.github.io)


Install
-------

     pip install sphinx

     cd docs/

     make html

View output in build/html/


Build Details
-------------

     cd docs/

     make {build}

Where \{build\} is one of:

**html**

Build HTML pages. This is the default builder.

**epub**

Build HTML files with additional information for building a documentation collection in one of these formats.

**latex**

Build LaTeX sources that can be compiled to a PDF document using pdflatex.

**linkcheck**

Check the integrity of all external links.


Edting
------

If you update any .less files, then recompile it via:

     npm install -g less

     cd docs/themes/boundless_android/static

     lessc style.less > style.css
     
Then from the root docs direcory do: 

      make html  

Please cp -R build/html output to the gh-pages branch of the boundlessmobile.github.io repository to update the live site loocated at:

   http://boundlessmobile.github.io



