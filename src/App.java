import EstructurasCatedra.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class App {
    static List<Ciudad> ciudades = new ArrayList<Ciudad>();
	static Grafo grafo;
    static Camino respuestaTSP,respuestaPasosObligados;

    public static void main(String[] args) throws Exception {
        cargarGrafo();
		//System.out.println(grafo.toString());
        HashSet<Ciudad> ciudadesObligadas;
        Ciudad origen, destino;

        origen=new Ciudad("Ushuaia", "Argentina", -54.0872, -68.3040, true);
        destino=new Ciudad("Cartagena de Indias", "Colombia", 10.3997, -75.5144, true);
        ciudadesObligadas=new HashSet<>();

        ciudadesObligadas.add(new Ciudad("Salar de Uyuni", "Bolivia", -20.1338, -67.4891, true));
        ciudadesObligadas.add(new Ciudad("Jericoacoara", "Brasil", -2.8714, -40.4916, true));
        ciudadesObligadas.add(new Ciudad("Glaciar Perito Moreno", "Argentina", -73.0481, 50.4732, true));
		ciudadesObligadas.add(new Ciudad("Machu Pichu", "Peru", -13.1631, -72.5456, true));
        
        respuestaPasosObligados=grafo.obtenerCaminoCortoConPasosObligados(origen, destino, ciudadesObligadas);
        respuestaTSP=grafo.obtenerCaminoCortoConPasosObligados(origen, destino, ciudadesObligadas);



		//respuestaTSP=grafo.cicloDeHamiltonPD(origen);
        System.out.println(respuestaTSP.toString());
    }

    public static void cargarGrafo(){
		grafo=new Grafo();
		cargarCiudades();

		for (int i=0;i<ciudades.size();i++){
			grafo.insertarVertice(ciudades.get(i));
		}
		cargarDistancias();
    }


	public static void cargarDistancias(){
		//asumimos que todas las ciudades estan conectadas entre si por rutas directas a falta de datos de prueba
		double cateto1,cateto2;
		int hip;
		for(int i=0;i<ciudades.size();i++){
			for(int j=i+1;j<ciudades.size();j++){
				cateto1=ciudades.get(i).getLatitud()-ciudades.get(j).getLatitud();
				cateto2=ciudades.get(i).getLatitud()-ciudades.get(j).getLatitud();
				hip=Math.round((float)Math.sqrt(Math.pow(cateto1, 2)+Math.pow(cateto2, 2))*1000);
				grafo.insertarArco(ciudades.get(i), ciudades.get(j), hip);
				//System.out.println("Ruta "+ciudades.get(i).getCiudad()+" - "+ciudades.get(j).getCiudad()+": "+hip+" kms");
			}
		}
	}

    public static void cargarCiudades(){
        // las 6 ciudades iniciales deben estar:
		// - Se parte desde Ushuaia
		// - Se llega a Cartagena de Indias
		// - Se debe pasar por el Salar de Uyuni
		// - Se debe pasar por Jericoacoara
		// - Se debe pasar por el Glaciar Perito Moreno
		// - Se debe pasar por Machu Pichu
		// el resto de las ciudades se pueden sacar para simplificar el problema

		/*
		ciudades.add(new Ciudad("Ciudad de Mendoza", "Argentina", -32.8908, -68.8272,false));
        ciudades.add(new Ciudad("Córdoba", "Argentina", -31.4135, -64.1811, false));
        ciudades.add(new Ciudad("Rosario", "Argentina", -32.9468, -60.6393, false));
        ciudades.add(new Ciudad("San Miguel de Tucumán", "Argentina", -26.8241, -65.2226, false));
        ciudades.add(new Ciudad("Cochabamba", "Bolivia", -17.3895, -66.1568, false));
        ciudades.add(new Ciudad("La Paz", "Bolivia", -16.5000, -68.1500, false));
        ciudades.add(new Ciudad("Laguna Verde", "Bolivia", -13.7000, -61.4333, false));
        ciudades.add(new Ciudad("Oruro", "Bolivia", -17.9833, -67.1500, false));
        ciudades.add(new Ciudad("Potosí", "Bolivia", -19.5836, -65.7531,false));
        ciudades.add(new Ciudad("Santa Cruz de la Sierra", "Bolivia", -17.7863, -63.1812, false));
        ciudades.add(new Ciudad("Sucre", "Bolivia", -19.0333, -65.2627, false));
        ciudades.add(new Ciudad("Tarija", "Bolivia", -21.5355, -64.7296, false));
		ciudades.add(new Ciudad("Amazonas", "Brasil", -64.5000, -3.7500, false));
		ciudades.add(new Ciudad("Belo Horizonte", "Brasil", -19.9208, -43.9378, false));
        ciudades.add(new Ciudad("Brasilia", "Brasil", -15.7797, -47.9297, false));
        
		ciudades.add(new Ciudad("Curitiba", "Brasil", -25.4278, -49.2731,false));
        ciudades.add(new Ciudad("Fernando de Noronha", "Brasil", -3.8507, -32.4200, false));
        ciudades.add(new Ciudad("Fortaleza", "Brasil", -3.7172, -38.5431, false));
        ciudades.add(new Ciudad("Ouro Preto", "Brasil", -20.3856, -43.5035, false));
		ciudades.add(new Ciudad("Río de Janeiro", "Brasil", -22.9064, -43.1822,false));
        ciudades.add(new Ciudad("Salvador", "Brasil", -12.9711, -38.5108,false));
        ciudades.add(new Ciudad("São Paulo", "Brasil", -23.5475, -46.6361,false));
        ciudades.add(new Ciudad("Antofagasta", "Chile", -23.6524, -70.3954,false));
        ciudades.add(new Ciudad("Desierto de Atacama", "Chile", -24.5000, -69.2500, false));
        ciudades.add(new Ciudad("Puente Alto", "Chile", -33.6117,-70.5758, false));
        ciudades.add(new Ciudad("Santiago de Chile", "Chile",-33.4569, -70.6483, false));
        ciudades.add(new Ciudad("Talcahuano", "Chile",-36.7249, -73.1168, false));
        ciudades.add(new Ciudad("Torres del Paine","Chile", -51.0503, -72.8295, false));
        ciudades.add(new Ciudad("Valparaíso","Chile", -33.0360, -71.6296, false));
        ciudades.add(new Ciudad("Viña del Mar", "Chile", -33.0246, -71.5518, false));
		ciudades.add(new Ciudad("Barranquilla", "Colombia", 10.9685, -74.7813,false));
        ciudades.add(new Ciudad("Bogotá", "Colombia", 4.6097, -74.0817,false));
        ciudades.add(new Ciudad("Bucaramanga", "Colombia", 7.1254, -73.1198,false));
        ciudades.add(new Ciudad("Cali", "Colombia", 3.4372, -76.5225,false));
		ciudades.add(new Ciudad("Cúcuta", "Colombia", 7.8939, -72.5078, false));
		ciudades.add(new Ciudad("Medellín", "Colombia", 6.2518, -75.5636, false));
		ciudades.add(new Ciudad("Pereira", "Colombia", 4.8133, -75.6961, false));
        ciudades.add(new Ciudad("Valle Cocora", "Colombia", 4.6411, -75.5414,false));
        ciudades.add(new Ciudad("Bosque Nuboso Monteverde", "Costa Rica", 10.3000, -84.8167, false));
        ciudades.add(new Ciudad("Baños", "Ecuador", -1.3964, -78.4247, false));
        ciudades.add(new Ciudad("Isla Galápagos", "Ecuador", -0.7402, 90.3138, false));
        ciudades.add(new Ciudad("Asunción", "Paraguay", -25.3007, -57.6359, false));
        ciudades.add(new Ciudad("Capiatá","Paraguay", -25.3552, -57.4454, false));
        ciudades.add(new Ciudad("Ciudad del Este", "Paraguay", -25.5097, -54.6111, false));
		ciudades.add(new Ciudad("Lambaré", "Paraguay", -25.3468, -57.6065, false));
		ciudades.add(new Ciudad("San Lorenzo", "Paraguay", -25.3397, -57.5088,false));
        ciudades.add(new Ciudad("Arequipa", "Peru", -16.3989, -71.5350,false));
        ciudades.add(new Ciudad("Chiclayo", "Peru", -6.7714, -79.8409, false));
        ciudades.add(new Ciudad("El Callao", "Peru", -12.0566, -77.1181, false));
        ciudades.add(new Ciudad("Huancayo", "Peru", -12.0651, -75.2049, false));
        ciudades.add(new Ciudad("Iquitos", "Peru", -3.7491, -73.2538, false));
        ciudades.add(new Ciudad("Lima", "Peru", -12.0432, -77.0282, false));
		ciudades.add(new Ciudad("Trujillo", "Peru", -8.1160, -79.0300, false));
		ciudades.add(new Ciudad("Las Piedras", "Uruguay", -34.7302, -56.2192, false));
        ciudades.add(new Ciudad("Montevideo", "Uruguay", -34.9033, -56.1882, false));
        ciudades.add(new Ciudad("Paysandú", "Uruguay", -32.3214, -58.0756, false));
        ciudades.add(new Ciudad("Punta del Este", "Uruguay", -34.9475, -54.9338, false));
        ciudades.add(new Ciudad("Rivera", "Uruguay", -30.9053, -55.5508, false));
        ciudades.add(new Ciudad("Salto", "Uruguay", -31.3833, -57.9667, false));
        ciudades.add(new Ciudad("Tacuarembó", "Uruguay", -31.7169,-55.9811, false));
        ciudades.add(new Ciudad("Barquisimeto", "Venezuela", 10.0647, -69.3570, false));
        ciudades.add(new Ciudad("Caracas", "Venezuela",10.4880, -66.8792, false));
        ciudades.add(new Ciudad("Maracaibo", "Venezuela", 10.6666, -71.6124, false));
        ciudades.add(new Ciudad("Maracay", "Venezuela", 10.2353, -67.5911, false));
        ciudades.add(new Ciudad("Valencia", "Venezuela",10.1620, -68.0077, false));
		*/


        // - Se parte desde Ushuaia
		// - Se llega a Cartagena de Indias
		// - Se debe pasar por el Salar de Uyuni
		// - Se debe pasar por Jericoacoara
		// - Se debe pasar por el Glaciar Perito Moreno
		// - Se debe pasar por Machu Pichu

        ciudades.add(new Ciudad("Ushuaia", "Argentina", -54.0872, -68.3040, true));
        ciudades.add(new Ciudad("Cartagena de Indias", "Colombia", 10.3997, -75.5144, true));

        ciudades.add(new Ciudad("Salar de Uyuni", "Bolivia", -20.1338, -67.4891, true));
        ciudades.add(new Ciudad("Jericoacoara", "Brasil", -2.8714, -40.4916, true));
        ciudades.add(new Ciudad("Glaciar Perito Moreno", "Argentina", -73.0481, 50.4732, true));
		ciudades.add(new Ciudad("Machu Pichu", "Peru", -13.1631, -72.5456, true));

        ciudades.add(new Ciudad("Tayrona", "Colombia", 11.3000, -74.1667, false));
		ciudades.add(new Ciudad("Maldonado", "Uruguay", -34.9000, -54.9500, false));


        

        for (int i=0;i<ciudades.size();i++){
            ciudades.get(i).setId(i);
        }
    }

}
