package mapas;


import com.sun.javafx.webkit.WebConsoleListener;

import controladores.principal.ControladorPrincipal;
import controladores.principal.TabEnderecoControlador;
import controladores.principal.TabSubterraneaController;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;


@SuppressWarnings("restriction")
public class GoogleMap extends Parent {
	
		// metodo  de chamada initMap com o mapa e webview //
		public GoogleMap() {
	        initMap();
	        initCommunication();
	        getChildren().add(webView);
	        
	       // setMarkerPosition(0,0);
	       // setMapCenter(0, 0);
	       // switchHybrid();
	        
		}
		
		// coordenadas para os cadastros de endereco e interferencias //
		String lat;
		String lon;
		
			 public String getLat() {
					return lat;
				}
	
				public void setLat(String lat) {
					this.lat = lat;
				}
	
				public String getLon() {
					return lon;
				}
	
				public void setLon(String lon) {
					this.lon = lon;
				}
		
				
		public void resizeWidthMap (Double wMap) {
			
	        webView.setMaxWidth(wMap);
	        webView.setMinWidth(wMap);
	       
		}
		
		public void resizeHeightMap (Double hMap) {
			
			webView.setMaxHeight(hMap);
			webView.setMinHeight(hMap);
	       
		}
		
		@SuppressWarnings("restriction")
		// inicializacao do webview e mapa html javascript //
	    void initMap()
	    {
	        webView = new WebView();
	        webEngine = webView.getEngine();
	        webView.setPrefWidth(1900);
	        webView.setPrefHeight(710);
	        webEngine.load(getClass().getResource("/html/mapas/Principal/index.html").toExternalForm()); // originalMap
	        
	        // mostrar erros no console
	        WebConsoleListener.setDefaultListener((webView, message, lineNumber, sourceId) -> {
	            System.out.println(message + "[at " + lineNumber + "]");
	        });
	        
	        ready = false;
	        
	        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
	        {
	            public void changed(final ObservableValue<? extends Worker.State> observableValue,
	                                final Worker.State oldState,
	                                final Worker.State newState)
	            {
	            	
	                if (newState == Worker.State.SUCCEEDED)
	                {
	                    ready = true;
	                }
	               // System.out.println(" initMap funcionando");
	            }
	        });
	    }

	    // metodo de comunicacao com o webEngine //
	    private void initCommunication() {
	        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
	        {
	            public void changed(final ObservableValue<? extends Worker.State> observableValue,
	                                final Worker.State oldState,
	                                final Worker.State newState)
	            {
	                if (newState == Worker.State.SUCCEEDED)
	                {
	                    doc = (JSObject) webEngine.executeScript("window");
	                    
	                    doc.setMember("app", GoogleMap.this);
	                    
	                    //doc.setMember("appShapeEndereco", GoogleMap.this);
	                }
	               
	               // System.out.println(" initComunicantion funcionando");
	            }
	        });
	    } 
	   
	    private void invokeJS(final String str) {
	    	
	        if(ready) {
	        	try {
	            doc.eval(str);
	             }
	        	catch (JSException js){ 
	            //System.out.println("nao ready execao de leitura javascript " + js);
	        	}
	        }
	        else {
	        	
	            webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
	            {
	                @Override
	                public void changed(final ObservableValue<? extends Worker.State> observableValue,
	                                    final Worker.State oldState,
	                                    final Worker.State newState)
	                {
	                    if (newState == Worker.State.SUCCEEDED)
	                    {
	                        doc.eval(str);
	                       
	                    }
	                   
		               // System.out.println(" invokeJS funcionando");
	                }
	            });
	            
	        }
	    }
	    
	    
	    public void setOnMapLatLngChanged(EventHandler<MapEvent> eventHandler) {
	        onMapLatLngChanged = eventHandler;
	    }
	    
	    public void handle(String typeCoordinate, String ddLatLon, String dmsLatLon, String utmLatLon) {
	  
	    	if (typeCoordinate.equals(" DD ")) {
	    		ControladorPrincipal.lblCoord1.setText(dmsLatLon);
				ControladorPrincipal.lblCoord2.setText(utmLatLon);
	    	}
	    	
	    	if (typeCoordinate.equals(" DMS ")) {
	    		ControladorPrincipal.lblCoord1.setText(ddLatLon);
				ControladorPrincipal.lblCoord2.setText(utmLatLon);
	    	}
	    	
	    	if (typeCoordinate.equals(" UTM ")) {
	    		ControladorPrincipal.lblCoord1.setText(ddLatLon);
				ControladorPrincipal.lblCoord2.setText(dmsLatLon);
	    	}
	    	
	    }
	    
	    // capturar evento de manipulacao do mapa em javascritp, adiquirir o poligono  (croqui) do endereco
	    public void handleShapeEndereco(String strShapeEndereco) {
	    	
	    	TabEnderecoControlador.tabEnderecoControlador.capturarCroquiEndereco(strShapeEndereco);
	    	
	    	System.out.println(strShapeEndereco);
	    	
	    }
 
	    public void setAllCoords(String dd, String dms, String utm) {

	    	ControladorPrincipal.lblDD.setText(dd);
	    	ControladorPrincipal.lblDMS.setText(dms);
	    	ControladorPrincipal.lblUTM.setText(utm);
	    	
	    }
	    
	    public void setCoords(String lat, String lon) {
	    	this.lat = lat;
	    	this.lon = lon;
	    

	    }

	    public void convDD (String typeCoord, String lat, String lon) {
	    
	    	invokeJS("obterUTMDMS(\'" + ""+ typeCoord + ""  + "\', \'" + "" + lat + "" + "\', \'" + ""+ lon + "" + "\')");
	    }
	    
	    public void convDMS (String typeCoordinate, String lat, String lon) {
	    	
	    	invokeJS("obterDDUTM(\'" + ""+ typeCoordinate + ""  + "\', \'" + "" + lat + ""  + "\', \'" + ""+ lon + "" + "\');"); 
	    	
	    }
	    
	    public void convUTM(String typeCoordinate, String latLon) {
	    	
	    	invokeJS("obterDDDMS(\'" + ""+ typeCoordinate + ""  + "\', \'" + ""+ latLon + "" + "\');"); 
	    	
	    }
	    
	    public void setEnderecoInterferencias (String strEndereco, String strInterferencias, String strDetalhes) {
	    	
	    	invokeJS("setEnderecoInterferencias(\'" + ""+ strEndereco + ""  
	    										+ "\', \'" + "" + strInterferencias + ""
	    										+ "\', \'" + "" + strDetalhes + "" 
	    										+ "\');"); 
	    	
	    }
	    
	    public void mostrarDemandas (String strEndereco, String strInfoDemandas) {
	    	
	    	invokeJS("mostrarDemandas(\'" + ""+ strEndereco + ""  
	    										+ "\', \'" + "" + strInfoDemandas + ""
	    										+ "\');"); 
	    	
	    }
	    
	    
	    public void setZoomIn() {
	    	invokeJS("setZoomIn();");
	    }
	    
	    public void setZoomOut() {
	    	invokeJS("setZoomOut();");
	    }
	    
	    public void openShape(int shape) {
	    	invokeJS("openShape(" + shape + ");");
	    }
	    
	    public void setMarkerPosition(String lat, String lng) {
	    	
	        invokeJS("setMarkerPosition('" + lat + "','" + lng + "')");
	       
	    }

	    public void setMapCenter(String lat, String lng) {

	        invokeJS("setMapCenter('" + lat + "','" + lng + "')");
	    }
	    
	    public void mudarEstiloMapa (int i) {
	    	
	    	invokeJS("mudarEstiloMapa(" + i + ")");
	    }
	    
	    public void setZoom (int i) {
	    	invokeJS("setZoom(" + i + ")");
	    }

	    public void switchSatellite() {
	        invokeJS("switchSatellite()");
	    }

	    public void switchRoadmap() {
	        invokeJS("switchRoadmap()");
	    }

	    public void switchHybrid() {
	        invokeJS("switchHybrid()");
	    }

	    public void switchTerrain() {
	        invokeJS("switchTerrain()");
	    }
	    
	    public void startJumping() {
	        invokeJS("startJumping()");
	    }

	    public void stopJumping() {
	        invokeJS("stopJumping()");
	    }
	    
	    public void setHeight(double h) {
	        webView.setPrefHeight(h);
	    }

	    public void setWidth(double w) {
	        webView.setPrefWidth(w);
	    }
	    // limpar o mapa dos marcadores e shapes
	    public void limparMapa () {
	    	invokeJS("limparMapa()");
	    	
	    }
	   
	    /**
	     * ativar a possibilidade de criacao de shape
	     */
	    public void criarShapeEndereco () {
	    	invokeJS("criarShapeEndereco()");
	    	
	    }
	    
	    /**
	     * setar poligono no mapa
	     * @param strShapeEndereco  - string com as coordenadas separadas: |-16.0351296510659,-48.10928962532557|-16.034510965864463,-48.108710268178356|...
	     */
	    public void setarPoligono(String strShapeEndereco) {
	    	
	    	invokeJS("setarPoligono('" + strShapeEndereco + "')");
	    	
	    }
	    
	    /**
	     * escolher criar utilizar um polyline ou polygon para a criacao da shape 
	     * @param b 
	     * 		b == true - polyline	b == false - polygon
	     */
	    public void setarLinhaOuShape(Boolean b) {
	    	
	    	invokeJS("setarLinhaOuShape(" + b + ")");
	    	
	    }
	    
	    /**
	     * abrir shapes como bacias hidrograficas, unidades hidrograficas etc
	     * @param strJson
	     * @param strShape
	     */
	    public void abrirShape (Boolean checkBox, String strJson, String strShape, String map ) {
	    	
	    	invokeJS("abrirShape("+checkBox+","+strJson+","+strShape+","+map+")");
	    	
	    }
	    /**
	     * 
	     * @param checkBox
	     * @param strJson
	     * @param strShape
	     * @param map
	     * @param lat
	     * @param lng
	     */
	    public void buscarPropriedadeShape (Boolean checkBox, String strJson, String strShape, String map , String lat, String lng) {
	 
	    	invokeJS("buscarPropriedadeShape("+checkBox+","+strJson+","+strShape+","+map+","+lat+","+lng+")");
	    	
	    }
	    
	    public void retornarCodigoSubsistema (String strSubsistema, String strCodigoSubsistema, String strVazaoMedia) {
	    	
	    	TabSubterraneaController.tabSubCon.retornarCodigoSubsistema (strSubsistema, strCodigoSubsistema, strVazaoMedia);
	    	
	    }

	    public ReadOnlyDoubleProperty widthProperty() {
	        return webView.widthProperty();
	    }
	    
	    private JSObject doc;
	    
	    @SuppressWarnings("unused")
		private EventHandler<MapEvent> onMapLatLngChanged;
	    
	    private WebView webView;
	    private WebEngine webEngine;
	    public boolean ready;
	    
}
