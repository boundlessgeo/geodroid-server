
App = function() {
    this.save = new OpenLayers.Strategy.Save();
    this.vector = new OpenLayers.Layer.Vector('Objects', {
        styleMap: new OpenLayers.StyleMap({
            'temporary': OpenLayers.Util.applyDefaults({
                pointRadius: 8,
            }, OpenLayers.Feature.Vector.style.temporary),

            'hover': OpenLayers.Util.applyDefaults({
                pointRadius: 8,
                fillColor: '#66cccc',
                strokeColor: '#66cccc',
                cursor: 'pointer'
            }, OpenLayers.Feature.Vector.style.temporary),

            'hover-delete': OpenLayers.Util.applyDefaults({
                pointRadius: 8,
                fillColor: 'red',
                strokeColor: 'red',
                strokeOpacity: 0.5,
                cursor: 'pointer'
            }, OpenLayers.Feature.Vector.style.temporary),

            'default': OpenLayers.Util.applyDefaults({
                pointRadius: 8,
                strokeWidth: 2,
            }, OpenLayers.Feature.Vector.style['default']),

            'select': OpenLayers.Util.applyDefaults({
                pointRadius: 8,
                strokeWidth: 2
            }, OpenLayers.Feature.Vector.style.select),

            'delete': new OpenLayers.Style({
                display: "none"
            }),
        }), 
        strategies: [new OpenLayers.Strategy.BBOX(), this.save],
        protocol: this.createProtocol()
    });

    this.initToolbar();
    this.initMap();
    this.initUI();

    // $('circle').hover(function() {
    //     console.log("in");
    // }, function() {
    //     console.log("out");
    // });
}

App.prototype.createProtocol = function() {
    var self = this;
    var http = new OpenLayers.Protocol.HTTP({
        url: "/features/world/cities",
        format: new OpenLayers.Format.GeoJSON()
    });

    var superCreate = OpenLayers.Protocol.HTTP.prototype.create;
    var superUpdate = OpenLayers.Protocol.HTTP.prototype.update;
    var superDelete = OpenLayers.Protocol.HTTP.prototype.delete;

    OpenLayers.Protocol.HTTP.prototype.create = function(features, options) {
        options.url = this.options.url + "?" + self.encodeCommitOptions();
        superCreate.apply(http, [features, options]);
    }
    OpenLayers.Protocol.HTTP.prototype.update = function(feature, options) {
        options.url = this.options.url + "/" + feature.fid + "?" + 
            self.encodeCommitOptions();
        superUpdate.apply(http, [feature, options]);
    }
    OpenLayers.Protocol.HTTP.prototype.delete = function(feature, options) {
        options.url = this.options.url + "/" + feature.fid + "?" + 
            self.encodeCommitOptions();
        superDelete.apply(http, [feature, options]);
    }
    return http;
}

App.prototype.initToolbar = function() {
    var toolbar = new OpenLayers.Control.Panel({
        displayClass: 'olControlEditingToolbar'
    });

    var edit = new OpenLayers.Control.ModifyFeature(this.vector, {
        vertexRenderIntent: 'temporary',
        title: "Edit",
        displayClass: 'app-control-edit',
    });
    
    var add = new OpenLayers.Control.DrawFeature(
        this.vector, OpenLayers.Handler.Point, {
            title: "Add",
            displayClass: 'app-control-add'
        });
    add.events.register('featureadded', this, function(e) {
        add.deactivate();
        edit.activate();
        edit.selectFeature(e.feature);
    });

    var del = new OpenLayers.Control.SelectFeature(this.vector,{
        title: "Delete",
        displayClass: 'app-control-delete'
    });
    del.events.register('featurehighlighted', this, function(e) {
        var f = e.feature;
        f.state = OpenLayers.State.DELETE;
        this.vector.redraw();
        $('#name').val("");
    });
    this.vector.events.register('beforefeaturemodified', this, function(e) {
        this.setCurrentFeature(e.feature);
    });

    toolbar.addControls([
        // this control is just there to be able to deactivate tools
        new OpenLayers.Control({
            title: "Pan",
            displayClass: 'app-control-pan'
        }),
        edit, del, add
    ]);

    this.toolbar = toolbar;
    this.tools = {
        add: add,
        edit: edit,
        'delete': del
    }
}

App.prototype.initMap = function() {
    var osm = new OpenLayers.Layer.OSM();
    osm.wrapDateLine = false;

    var blank = new OpenLayers.Layer("Blank", {
        isBaseLayer: true
    });

    var map = new OpenLayers.Map({
        div: 'map',
        projection: 'EPSG:4326',
        numZoomLevels: 18,
        controls: [
            new OpenLayers.Control.TouchNavigation({
                dragPanOptions: {
                    enableKinetic: true
                }
            }),
            new OpenLayers.Control.Zoom(),
            //new OpenLayers.Control.LayerSwitcher(),
            this.toolbar
        ],
        layers: [osm, blank, this.vector],
        center: new OpenLayers.LonLat(0, 0),
        zoom: 1,
        theme: null
    });

    this.toolbar.controls[0].activate();
    map.zoomToExtent(new OpenLayers.Bounds(
        -14636773.67,1379535.48,-2856910.36,11613536.33), 3);
    
    this.map = map;
}

App.prototype.initUI = function() {
    var self = this;
    $('#name').blur(function(e) {
        var f = self.currentFeature;
        if (!isNull(f)) {
            f.attributes.name =  $('#name').val();
            if (isNull(f.state)) {
                f.state = OpenLayers.State.UPDATE;
            }
        }
    });

    $('#save').click(function(e) {
        self.save.save();
        $('#status').html("Saved.").addClass("info");
    });
}

App.prototype.encodeCommitOptions = function() {
    var opts = "options=";
    var msg = $('#message').val();
    if (!isNull(msg)) {
        opts += "message:" + msg + ";";
    }
    var author = $('#author').val();
    if (!isNull(msg)) {
        opts += "author:" + author + ";";
    }
    return opts;
}

App.prototype.setCurrentFeature = function(f) {
    var name = f.attributes.name;
    $('#name').val(isNull(name) ? "" : name).focus();
    this.currentFeature = f;
}

function init() {
    app = new App();
}

function isNull(val) {
    return val == null || val == "" || typeof val == 'undefined';
}