package edu.csre.gnr629project.client;



/**
 * Make sure you have the gwt-openlayers library that is available at 
 * http://sourceforge.net/projects/gwt-openlayers/files/gwt-openlayers/
 */
/**
 * 1. put this line in YOURPROJECTNAME.gwt.xml (look for it in the src folder and under your project
 * package name. Open it with the builtin text editor (right click on the .xml file and then openwith ...): 
 * <inherits name='org.gwtopenmaps.openlayers.OpenLayers'/>
 * 
 * 2.put this in the HTMl file <script src="http://openlayers.org/api/2.11/OpenLayers.js"></script>
 * 3. put this in the html file <script src="http://maps.google.com/maps/api/js?v=3&sensor=false"></script>
 * After this step you are ready to use the gwt-openlayers library.
 * 
 */

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
//import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import org.gwtopenmaps.openlayers.client.control.OverviewMap;

import org.gwtopenmaps.openlayers.client.Bounds;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;

import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;

import org.gwtopenmaps.openlayers.client.control.MouseDefaults;
import org.gwtopenmaps.openlayers.client.control.MouseToolbar;
import org.gwtopenmaps.openlayers.client.control.PanZoomBar;
import org.gwtopenmaps.openlayers.client.control.Scale;
import org.gwtopenmaps.openlayers.client.control.ScaleLine;
import org.gwtopenmaps.openlayers.client.layer.Google;
import org.gwtopenmaps.openlayers.client.layer.Layer;

import org.gwtopenmaps.openlayers.client.layer.WMS;
import org.gwtopenmaps.openlayers.client.layer.WMSParams;

import org.gwtopenmaps.openlayers.client.util.JObjectArray;
import org.gwtopenmaps.openlayers.client.util.JSObject;

import org.gwtopenmaps.openlayers.client.layer.GMapType;

import org.gwtopenmaps.openlayers.client.layer.GoogleOptions;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3MapType;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3Options;
import org.gwtopenmaps.openlayers.client.layer.WMSOptions;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GNR629Project implements EntryPoint {
	   private static final Projection DEFAULT_PROJECTION = new Projection(
       "EPSG:4326");
	
	private MapWidget mapWidget;
	private Map map;
	private WMS wmsLayer,wmsLayer2,wmsLayer3;
	private GMapType gType;
	private String ss;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		MapOptions mapOptions = new MapOptions();
		mapOptions.setControls(new JObjectArray(new JSObject[] {}));
		mapOptions.setNumZoomLevels(15);
		mapOptions.setProjection("EPSG:4326");

		// let’s create map widget and map objects
		mapWidget = new MapWidget("900px", "600px", mapOptions);
		map = mapWidget.getMap();
/*
        // Google map layers ...........................................................................................//
		     /*
		        GoogleV3Options gNormalOptions = new GoogleV3Options();
		        gNormalOptions.setIsBaseLayer(true);
		        gNormalOptions.setType(GoogleV3MapType.G_NORMAL_MAP);
		        GoogleV3 gNormal = new GoogleV3("Google Normal", gNormalOptions);
		      	        
        // adding google Base Map layer on map ...............................................................................//
		    
		        map.addLayer(gNormal);
		        gNormal.setIsVisible(true);

		// let’s create WMS map layer
		WMSParams wmsParams = new WMSParams();
		wmsParams.setFormat("image/png");
		wmsParams.setLayers("powai:hospitals");
		wmsParams.setIsTransparent(true);
	    wmsParams.setStyles("");
	    WMSOptions wmsLayerParams = new WMSOptions();
	    wmsLayerParams.setDisplayOutsideMaxExtent(true);
		wmsLayerParams.setTransitionEffectResize();
		wmsLayer = new WMS ("Hospitals","http://localhost:8080/geoserver/wms",wmsParams,wmsLayerParams);
		wmsLayer.setIsBaseLayer(true);	  
		
		// more WMS layers
		// lets create WMS base map layer
        
        WMSParams wmsParams2 = new WMSParams();
        wmsParams2.setIsTransparent(true); // to make base layer remove transperency 
        wmsParams2.setFormat("image/png");
        wmsParams2.setLayers("layers:incidents");
        wmsParams2.setStyles("");
                 
        // lets create new WMS map layer
        WMSParams wmsParams3 = new WMSParams();
        wmsParams3.setIsTransparent(true);
        wmsParams3.setFormat("image/png");
        wmsParams3.setLayers("layers:Routes to Hospital");
        //wmsParams3.setStyles("cite_lakes");
     		
        wmsLayer2 = new WMS ("Incidents","http://localhost:8080/geoserver/wms",wmsParams2,wmsLayerParams);
        wmsLayer3 = new WMS ("Routes","http://localhost:8080/geoserver/wms",wmsParams3,wmsLayerParams);
	
		// let’s add layers and controls to map
			
        map.addControl(new LayerSwitcher()); 
        map.addControl(new OverviewMap()); 
        map.addControl(new ScaleLine()); 
        map.addControl(new MouseDefaults());
		map.addControl(new PanZoomBar());
			
		// 3 layers 
		//map.addLayers(new Layer[] {gNormal,wmsLayer,wmsLayer2,wmsLayer3});
		map.addLayer(wmsLayer);
		LonLat lonLat1 = new LonLat(72.8777, 19.0760);
        lonLat1.transform(DEFAULT_PROJECTION.getProjectionCode(),
                       map.getProjection());
        map.setCenter(lonLat1, 12);
        */

	    /*
	     * GUI 
	     * 
	     */
        // Main dock panel
		DockPanel dockPanel = new DockPanel();
	    dockPanel.setStyleName("cw-DockPanel");
	    dockPanel.setSpacing(4);
	    dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
	    dockPanel.add(new HTML("Interoperatable Web Service Client"), DockPanel.NORTH);
		dockPanel.setBorderWidth(5);
	            
	    // Create the tab panels for wms, wcs, wfs
	    TabPanel tabs = new TabPanel();
	    tabs.add(wmstab(map), "WMS");
	    tabs.add(wfstab(map), "WFS");
	    tabs.add(wcstab(map), "WCS");
	    tabs.setWidth("100%");
	    tabs.selectTab(0);
	    
	    //Add the tabs to the main dock panel
	    dockPanel.add(tabs, DockPanel.WEST);
	    
	    //Add the map widget to the main dock panel
	    dockPanel.add(mapWidget, DockPanel.NORTH);
   
	    RootPanel.get().add(dockPanel);
      		 
	}

	public Widget wcstab(Map map) {
		/*
		 * when wcs tab is selected
		 * display the option to select the server
		 */
		 FlexTable grid = new FlexTable();
		 final ListBox serverListbox = new ListBox();
		 final ListBox wcsRequestListbox = new ListBox();
		 final ListBox wcsLayersListbox = new ListBox();
		 //final ListBox FormatListbox = new ListBox();
			final Button submit = new Button();
			submit.setSize("100px", "40px");
			submit.setText("Submit");
			grid.setBorderWidth(0);
			grid.setHTML(0,0,"Server:");
			grid.setWidget(0, 1, serverListbox);
			grid.setHTML(1,0,"Request");
			grid.setWidget(1, 1, wcsRequestListbox);
			grid.setHTML(2,0,"Layers");
			grid.setWidget(2, 1, wcsLayersListbox);
			grid.setWidget(3, 1,submit);
			
		    VerticalPanel wcsPanel = new VerticalPanel();
		    wcsPanel.setSize("500px", "600px");
		    wcsPanel.add(grid);
		    
		    //AbsolutePanel xmlpanel = new AbsolutePanel();
		    //xmlpanel.setSize("400px", "400px");
		    	//xmlpanel.add(new HTML("XML response"),20,10);
		    	String xmldoc = "This is a ScrollPanel contained at the center of a DockPanel. By putting some fairly large contents in the middle and setting its size explicitly, it becomes a scrollable area within the page, but without requiring the use of an IFRAME.\r\n" + 
		    			"\r\n" + 
		    			"Here's quite a bit more meaningless text that will serve primarily to make this thing scroll off the bottom of its visible area. Otherwise, you might have to make it really, really small in order to see the nifty scroll bars!";
		    	HTML xmlresponse = new HTML(xmldoc);
		    	//xmlpanel.add(xmlresponse,20,30);
			    ScrollPanel scroller = new ScrollPanel(xmlresponse);
			    scroller.setSize("400px", "300px");
			//xmlpanel.add(scroller);
		    wcsPanel.add(scroller);
		    return wcsPanel;

	}

	public Widget wfstab(Map map) {
		 AbsolutePanel wfsPanel = new AbsolutePanel();
		 FlexTable grid = new FlexTable();

		    wfsPanel.setSize("500px", "430px");
		    wfsPanel.add(grid, 10,10);
		    return wfsPanel;
	}

	public Widget wmstab(Map map) {
		// WMS tab initial GUI
		    FlexTable grid = new FlexTable();
		    final ListBox serverListbox = new ListBox();
		    final ListBox wmsRequestListbox = new ListBox();
		    final ListBox wmsLayersListbox = new ListBox();
		    final ListBox CRSListbox = new ListBox();
		    final ListBox FormatListbox = new ListBox();
			final TextBox minX = new TextBox();
			final TextBox minY = new TextBox();
			final TextBox maxX = new TextBox();
			final TextBox maxY = new TextBox();
			/*
			Bounds bWMS = map.getExtent();
			minX.setText(String.valueOf(bWMS.getLowerLeftX()));
			minY.setText(String.valueOf(bWMS.getLowerLeftY()));
			maxX.setText(String.valueOf(bWMS.getUpperRightX()));
			maxY.setText(String.valueOf(bWMS.getUpperRightY()));
			*/
			
			
			final Button submit = new Button();
			submit.setSize("100px", "40px");
			submit.setText("Submit");
			
					
			grid.setBorderWidth(0);
			grid.setHTML(0,0,"Server:");
			grid.setWidget(0, 1, serverListbox);
			grid.setHTML(1,0,"Request");
			grid.setWidget(1, 1, wmsRequestListbox);
			grid.setHTML(2,0,"Layers");
			grid.setWidget(2, 1, wmsLayersListbox);
			grid.setHTML(3,0,"CRS");
			grid.setWidget(3, 1, CRSListbox);
			grid.setHTML(4,0,"Format");
			grid.setWidget(4, 1, FormatListbox);
			grid.setHTML(5,0,"MinX");
			grid.setHTML(5,1,"MinY");
			grid.setWidget(6,0,minX);
			grid.setWidget(6,1,minY);
			grid.setHTML(7,0,"MaxX");
			grid.setHTML(7,1,"MaxY");
			grid.setWidget(8,0,maxX);
			grid.setWidget(8,1,maxY);
			grid.setWidget(9, 1,submit);
			/*
		    grid.setHTML(12, 0, "XML Response");
		    FlexCellFormatter cellFormatter = grid.getFlexCellFormatter();

		    cellFormatter.setHorizontalAlignment(
		            10, 0, HasHorizontalAlignment.ALIGN_CENTER);
		    cellFormatter.setColSpan(12, 0, 2);
		    cellFormatter.setRowSpan(12, 0, 5);
		    
		    AbsolutePanel wmsPanel = new AbsolutePanel();
		    wmsPanel.setSize("500px", "430px");
		    wmsPanel.add(grid, 20, 20);
		    */
		    VerticalPanel wmsPanel = new VerticalPanel();
		    wmsPanel.setSize("500px", "600px");
		    wmsPanel.add(grid);
		    
		    	
		    	String xmldoc = "This is a ScrollPanel contained at the center of a DockPanel. By putting some fairly large contents in the middle and setting its size explicitly, it becomes a scrollable area within the page, but without requiring the use of an IFRAME.\r\n" + 
		    			"\r\n" + 
		    			"Here's quite a bit more meaningless text that will serve primarily to make this thing scroll off the bottom of its visible area. Otherwise, you might have to make it really, really small in order to see the nifty scroll bars!";
		    	HTML xmlresponse = new HTML(xmldoc);
		    	//xmlpanel.add(xmlresponse,20,30);
			    ScrollPanel scroller = new ScrollPanel(xmlresponse);
			    scroller.setSize("400px", "300px");
			//xmlpanel.add(scroller);
		    wmsPanel.add(scroller);
		    
		    
		    serverListbox.addItem("GEOSERVER");
		    serverListbox.addItem("External");
		    serverListbox.addChangeHandler(new ChangeHandler() {

		        @Override
		        public void onChange(ChangeEvent event) {
		            onChangeServerWMS(serverListbox);
		        }

				public void onChangeServerWMS(ListBox lb) {
					// When server changes
					
					// lb.getValue(lb.getSelectedIndex()) contains the value of the server 
					// Create url for the get capabilities of this server
					
					//lb.getValue(lb.getSelectedIndex())+"?request=getCapabilities");//"http://localhost:8080/geoserver/wms?request=getCapabilities"
		  //		//"http://localhost:8080/geoserver/wms?request=getCapabilities");
					String wmsurl = "http://localhost:8080/geoserver/wms?request=getCapabilities";
					RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, wmsurl);
					
					// send the get capab request
					
					//
					
					try {
					      builder.sendRequest(null,new RequestCallback() {

					        public void onError(Request request, Throwable exception) {
					        	//Window.alert("Error Occured while sending requset");
					        }

					        public void onResponseReceived(Request request, Response response) {
					            if (200 == response.getStatusCode()) {
					                // Process the response in response.getText()
									String xmlResponse = response.getText();
									try {
									    // parse the XML document into a DOM
									    Document messageDom = (Document) XMLParser.parse(xmlResponse);
									    // populate the list with requests name
									    Node r = (Node) messageDom.getElementsByTagName("Request").getItem(0);
									    NodeList requests = (NodeList)r.getChildNodes();
									    wmsRequestListbox.clear();
										//grid.setWidget(1, 1, wmsLayersList);
								
									    for(int i=0;i<requests.getLength();i++){
									    	if(requests.item(i).getNodeType() == Node.ELEMENT_NODE){
									    		wmsRequestListbox.addItem(requests.item(i).getNodeName());	
									    	}
									    }
									    // populate the list with the layers name
									    NodeList layers = (NodeList) messageDom.getElementsByTagName("Layer");
									    wmsLayersListbox.clear();
									    for(int i=1;i<layers.getLength();i++){
									    	Node layerNameNode = ((Element)layers.item(i)).getElementsByTagName("Name").item(0);
									    	String layerName = layerNameNode.getFirstChild().getNodeValue();
									    	wmsLayersListbox.addItem(layerName);
									    }
										//grid.setWidget(2, 1, wmsOperationsList);

					  
					}catch (Exception e) {
					      System.out.println("Failed to send the request: " + e.getMessage());
					    }
					            }
					        }
					      });
						}catch (RequestException e) {
						      System.out.println("Failed to send the request: " + e.getMessage());
						    }		
					
				}
		    });
		    
		
		

		    
		    		
		return wmsPanel;
	}




}
