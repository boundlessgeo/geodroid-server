Service Reference
=================

Documentation Conventions

-------------------------

Method and URL
  For each API endpoint, the HTTP method and URL are noted with URL path fragment
  parameters.

URL Parameters
  {parameter_name} denotes a required path fragment

  [{optional_element}] denotes an optional path fragment

Query String
  Any optional URL query parameters will be noted. These are provided in the URL
  using ?name=value

Request Body
  If required, an example of a request body.

Expected Responses
  Response descriptions will first describe the HTTP response code and content-type.

  Following will be an example of the response body.

Potential Errors
  Potential errors will be noted with their HTTP status code and message (messages
  may subject to changes but along with the status code, should note the nature of the
  problem).


The HTTP services provided by GeoDroid Server are outlined below.

.. toctree::
   :maxdepth: 2

   data-service
   tile-service
   feature-service