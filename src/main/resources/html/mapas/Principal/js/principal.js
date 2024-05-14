
var map;
var marker;
var markers = [];
var layers = [];
var linhas = []; // para alocar as polylines que ligam os marcadores endereço interferencia

/**
 * leitor provisorio de json, utiliza uma tab input no index html que le uma variavel (var = bacias, p. ex) com o conteudo json
 */
var geoJsonBacias = JSON.parse(bacias);
var geoJsonUnidadesHidrograficas = JSON.parse(unidades);
var geoJsonFraturado = JSON.parse(fraturado);
var geoJsonFreatico = JSON.parse(freatico);
var geoJsonSuperficial = JSON.parse(superficial);

/*
  poligono, infoWindow e as propriedades do poligono
  */
 var polygon, infoWindow, propsPolygon;
 var infoWidowsArray = [];

 
/**
 * colecao de poligonos e multipoligonos com suas propriedades (por ex. polygon.properties.Cod_plan)
 */
var shapeBacias = [];
var shapeUnidadesHidrograficas = [];
var shapeFraturado = [];
var shapeFreatico = [];

 

function initialize() {

	var defLatLng = new google.maps.LatLng(-1, -47);
		/* centralizar o mapa no retangulo do DF = (-15.790631073109617, -47.74939032660592);
		 * 	No caso, -2,-47, está centralizado para aparecer melhor o mapa no zoom 2
		 */
	// coordenada da adasa para o primeiro marcador
	var adasa = new google.maps.LatLng(-15.775073004902042, -47.940351677729836); // coordenada adasa
	
	// opcoes do mapa //
	var mapOptions = {
			center: defLatLng,
	        zoom: 2,
	        mapTypeId: google.maps.MapTypeId.ROADMAP,
	        scaleControl: true,
	        disableDefaultUI: true, // desabilitar controles nativos como zoom etc
	        styles: styles.darkBlue // cor inicial do mapa
	    };
	
	// mapa //
	map = new google.maps.Map(document.getElementById("map"), mapOptions);
	
	// primeiro marcador //
	marker = new google.maps.Marker({
	    position: adasa,
	    map: map,
	    title: 'Adasa'
	 });
	
	// ouvinte para dar zoom ao clicar no primeiro marcador //
	marker.addListener('click', function() {
	    map.setZoom(12);
	    map.setCenter(marker.getPosition());
	    
	  });
	
	// ouvinte para  obter  as coordenadas do local clicado no  mapa //
	google.maps.event.addListener(map, 'click', getCoordClick);

  //-- inicializar as variaveis de shapes --//
  layers[0] = new google.maps.KmlLayer('https://sites.google.com/site/shapeskmlkmz/Unidades_Hidrograficas_70.kmz',{ preserveViewport: true });

  layers[1] = new google.maps.KmlLayer('https://sites.google.com/site/shapeskmlkmz/riosDF110000.kmz',{ preserveViewport: true });

  layers[2] = new google.maps.KmlLayer('https://sites.google.com/site/shapeskmlkmz/fraturadoDF.kmz',{ preserveViewport: true });

  layers[3] = new google.maps.KmlLayer('https://sites.google.com/site/shapeskmlkmz/PorosoDF.kmz',{ preserveViewport: true });

  layers[4] = new google.maps.KmlLayer('https://sites.google.com/site/shapeskmlkmz/riosUniao.kmz',{ preserveViewport: true });

  layers[5] = new google.maps.KmlLayer('https://sites.google.com/site/shapeskmlkmz/zonasUTM.kmz',{ preserveViewport: true });

  layers[6] = new google.maps.KmlLayer('https://sites.google.com/site/shapeskmlkmz/baciasHidrograficas.kmz',{ preserveViewport: true });
   
  layers[7] = new google.maps.TrafficLayer();


  google.maps.event.addDomListener(window, 'load', initialize);

}


