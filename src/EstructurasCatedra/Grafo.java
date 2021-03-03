package EstructurasCatedra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import utiles.TecladoIn;

import java.util.HashSet;

public class Grafo {
	private NodoVertice inicio;

	public Grafo() {
		inicio = null;
	}

	public boolean insertarVertice(Object elem) {
		boolean exito = true;
		NodoVertice aux,temp;
		boolean excep = false;


		aux= new NodoVertice(elem);
		if (inicio == null) {
			inicio = aux;
		} else {
			temp = inicio;
			while (temp.getSigVertice() != null && !excep) {
				if (temp.getElem().equals(elem)) {
					excep = true;
				} else {
					temp = temp.getSigVertice();
				}
			}
			if (temp.getElem().equals(elem)) {
				excep = true;
			}
			if (excep) {
				exito = false;
			} else {
				temp.setSigVertice(aux);
			}
		}
		return exito;
	}

	public boolean insertarArco(Object vert1, Object vert2, Object etiqueta) {
		NodoVertice aux1 = buscarNodoVertice(inicio, vert1);
		boolean excep = true, exito = true;
		if (aux1 != null) {
			NodoVertice aux2 = buscarNodoVertice(inicio, vert2);
			if (aux2 != null) {
				NodoVertice temp2 = new NodoVertice(vert2);
				if (recuperarArco(aux1.getAdyacente(), temp2) == null) {
					if (aux1.getAdyacente() != null) {
						NodoAdyacente auxAD1 = aux1.getAdyacente();
						while (auxAD1.getSigAdyacente() != null) {
							auxAD1 = auxAD1.getSigAdyacente();
						}
						auxAD1.setSigAdyacente(new NodoAdyacente(aux2, etiqueta));
					} else {
						aux1.setAdyacente(new NodoAdyacente(aux2, etiqueta));
					}
					if (aux2.getAdyacente() != null) {
						NodoAdyacente auxAD2 = aux2.getAdyacente();
						while (auxAD2.getSigAdyacente() != null) {
							auxAD2 = auxAD2.getSigAdyacente();
						}
						auxAD2.setSigAdyacente(new NodoAdyacente(aux1, etiqueta));
					} else {
						aux2.setAdyacente(new NodoAdyacente(aux1, etiqueta));
					}
					excep = false;
				}
			}
		}
		if (excep) {
			exito = false;
		}
		// Suponiendo que no se ingresen mas de un arco entre los mismos dos nodos:

		return exito;
	}


	public boolean eliminarVertice(Object vert) {
		int ind = elimCompleto(inicio, new NodoVertice(vert), 0);
		boolean exito = true;
		if (ind == 1) {
			inicio = inicio.getSigVertice();
		}
		if (ind == 0) {
			exito = false;
		}
		return exito;
	}

	private int elimCompleto(NodoVertice aux, NodoVertice busq, int enc) {
		int ret = 0;
		if (aux != null) {
			if (enc == 1) {
				eliminarArco(aux, aux.getAdyacente(), busq);
				elimCompleto(aux.getSigVertice(), busq, enc);
			} else {
				if (aux.getElem().equals(busq.getElem())) {
					elimCompleto(aux.getSigVertice(), busq, 1);
					ret = 1;
				} else {
					ret = elimCompleto(aux.getSigVertice(), busq, enc);
					if (ret == 1 || ret == 2) {
						eliminarArco(aux, aux.getAdyacente(), busq);
						if (ret == 1) {
							aux.setSigVertice(aux.getSigVertice().getSigVertice());
							ret = 2;
						}
					}
				}
			}
		}
		return ret;
	}

	public boolean eliminarArco(Object vert1, Object vert2) {
		boolean exito = true;
		NodoVertice aux1 = buscarNodoVertice(inicio, vert1);
		boolean excep = true;
		if (aux1 != null) {
			NodoVertice aux2 = buscarNodoVertice(inicio, vert2);
			if (aux2 != null) {
				NodoVertice temp2 = new NodoVertice(vert2);
				if (recuperarArco(aux1.getAdyacente(), temp2) != null) {
					eliminarArco(aux1, aux1.getAdyacente(), new NodoVertice(vert2));
					eliminarArco(aux2, aux2.getAdyacente(), new NodoVertice(vert1));
					excep = false;
				}
			}
		}
		if (excep) {
			exito = false;
		}
		return exito;
	}

	private void eliminarArco(NodoVertice inicio, NodoAdyacente aux, NodoVertice nodo) {
		if (aux != null) {
			NodoAdyacente sig = aux;
			if (sig.getVerticeAdy().getElem().equals(nodo.getElem())) {
				inicio.setAdyacente(sig.getSigAdyacente());
				sig = null;
			} else {
				while (sig != null) {
					if (sig.getVerticeAdy().getElem().equals(nodo.getElem())) {
						aux.setSigAdyacente(sig.getSigAdyacente());
						sig = null;
					} else {
						aux = sig;
						sig = sig.getSigAdyacente();
					}
				}
			}
		}
	}

	public int cantidadDeCaminos(Object v1, Object v2) {
		Lista aux;
		aux = this.caminosSimples(v1, v2);
		return aux.longitud();
	}

	public Lista caminosSimples(Object v1, Object v2) {
		Lista ret = new Lista();
		NodoVertice vert = buscarNodoVertice(inicio, v1);
		if (vert != null) {
			Lista aux = new Lista();
			caminosSimples(aux, ret, vert, new NodoVertice(v2));
		}
		ret.invertirLista();
		return ret;
	}

	private void caminosSimples(Lista aux, Lista ret, NodoVertice vert, NodoVertice v2) {
		aux.insertar(vert.getElem(), 1);

		if (vert.getElem().equals(v2.getElem())) {
			Lista aux2 = aux.clone();
			aux2.invertirLista();
			ret.insertar(aux2, 1);
		} else {
			NodoAdyacente temp = vert.getAdyacente();
			while (temp != null) {
				if (aux.localizar(temp.getVerticeAdy().getElem()) == -1) {
					caminosSimples(aux, ret, temp.getVerticeAdy(), v2);
				}
				temp = temp.getSigAdyacente();
			}
		}
		aux.eliminar(1);
	}

	public Lista caminoMasCorto(Object v1, Object v2) {
		Lista ret = new Lista();
		NodoVertice temp = buscarNodoVertice(inicio, v1);
		if (temp != null) {
			ret = caminoCorto(new Lista(), ret, temp, new NodoVertice(v2));
		}
		ret.invertirLista();
		return ret;
	}

	private Lista caminoCorto(Lista aux, Lista ret, NodoVertice vert, NodoVertice v2) {
		aux.insertar(vert.getElem(), 1);

		// System.out.println(aux.toString());
		if (vert.getElem().equals(v2.getElem())) {
			if (ret.esVacia()) {
				ret = aux.clone();
			} else {
				if (aux.longitud() < ret.longitud()) {
					ret.vaciar();
					ret = aux.clone();
				}
			}
		} else {
			NodoAdyacente adyancente = vert.getAdyacente();
			while (adyancente != null) {
				if (aux.localizar(adyancente.getVerticeAdy().getElem()) == -1) {
					if (ret.esVacia()) {
						ret = caminoCorto(aux, ret, adyancente.getVerticeAdy(), v2);
					} else {
						if (ret.longitud() > aux.longitud() + 1) {
							ret = caminoCorto(aux, ret, adyancente.getVerticeAdy(), v2);
						}
					}
				}
				adyancente = adyancente.getSigAdyacente();
			}
		}
		aux.eliminar(1);
		return ret;
	}

	public Lista caminoMasLargo(Object v1, Object v2) {
		Lista ret = new Lista();
		NodoVertice temp = buscarNodoVertice(inicio, v1);
		if (temp != null) {
			ret = caminoLargo(new Lista(), ret, temp, new NodoVertice(v2));
		}
		ret.invertirLista();
		return ret;
	}

	private Lista caminoLargo(Lista aux, Lista ret, NodoVertice vert, NodoVertice v2) {
		aux.insertar(vert.getElem(), 1);
		if (vert.getElem().equals(v2.getElem())) {
			if (aux.longitud() > ret.longitud()) {
				ret.vaciar();
				ret = aux.clone();
			}
		} else {
			NodoAdyacente temp = vert.getAdyacente();
			while (temp != null) {
				if (aux.localizar(temp.getVerticeAdy().getElem()) == -1) {
					ret = caminoLargo(aux, ret, temp.getVerticeAdy(), v2);
				}
				temp = temp.getSigAdyacente();
			}
		}
		aux.eliminar(1);
		return ret;
	}

	public boolean existeCamino(Object v1, Object v2) {
		NodoVertice temp = buscarNodoVertice(inicio, v1);
		if (temp != null) {
			Lista lis = new Lista();
			NodoVertice nv2 = new NodoVertice(v2);
			lis.vaciar();
			return caminoInt(temp, nv2, lis);
		} else {
			return false;
		}
	}

	private boolean caminoInt(NodoVertice aux, NodoVertice v2, Lista lis) {
		if (aux.equals(v2)) {
			return true;
		} else {
			lis.insertar(aux, 1);
			NodoAdyacente temp = aux.getAdyacente();
			boolean enc = false;
			while (!enc && temp != null) {
				if (lis.localizar(temp.getVerticeAdy()) == -1) {
					enc = caminoInt(temp.getVerticeAdy(), v2, lis);
				}
				temp = temp.getSigAdyacente();
			}
			return enc;
		}
	}

	public Lista obtenerVertices() {
		Lista ret = new Lista();
		NodoVertice aux = inicio;
		while (aux != null) {
			visitar(aux, ret);
			aux = aux.getSigVertice();
		}
		return ret;
	}

	private void visitar(NodoVertice aux, Lista visit) {
		if (visit.localizar(aux.getElem()) == -1) {
			visit.insertar(aux.getElem(), 1);
			NodoAdyacente temp = aux.getAdyacente();
			while (temp != null) {
				visitar(temp.getVerticeAdy(), visit);
				temp = temp.getSigAdyacente();
			}
		}
	}

	public boolean actualizarEtiqueta(Object nombre1, Object nombre2, Object etiqueta) {
		boolean exito = false;
		NodoVertice aux1 = buscarNodoVertice(inicio, nombre1);
		if (aux1 != null) {
			NodoAdyacente temp = aux1.getAdyacente();
			while (temp != null && !(temp.getVerticeAdy().getElem().equals(nombre2))) {
				temp = temp.getSigAdyacente();
			}
			if (temp != null) {
				temp.setEtiqueta(etiqueta);
				exito = true;
			}
		}
		return exito;
	}

	public NodoAdyacente recuperarArco(NodoAdyacente aux, NodoVertice vert) {
		if (aux != null) {
			if (aux.getVerticeAdy().getElem().equals(vert.getElem())) {
				return aux;
			} else {
				return recuperarArco(aux.getSigAdyacente(), vert);
			}
		} else {
			return null;
		}
	}

	private NodoVertice buscarNodoVertice(NodoVertice aux, Object elem) {

		if (aux != null) {
			if (aux.getElem().equals(elem)) {
				return aux;
			} else {
				return buscarNodoVertice(aux.getSigVertice(), elem);
			}
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		String cadena = "";
		NodoVertice aux = inicio;
		NodoAdyacente auxA;
		while (aux != null) {
			cadena+="Nodo "+ aux.getElem().toString()+": ";
			auxA=aux.getAdyacente();
			if(auxA==null){
				cadena+= "sin vecinos";
			}else{
				while(auxA!=null){
					cadena+=auxA.getVerticeAdy().getElem().toString()+"("+auxA.getEtiqueta()+"), ";
					auxA=auxA.getSigAdyacente();
				}
				cadena=cadena.substring(0,cadena.length()-2);
			}
			cadena = cadena + "\n";
			aux = aux.getSigVertice();
		}
		if (cadena.equals("")) {
			cadena = "El grafo está Vací­o";
		}
		return cadena;
	}


	public Camino caminoMenorDistancia(Object origen, Object destino, LinkedList<Ciudad> obligatorios) {
		// Se busca el camino más corto en distancia entre el origen y destino
		NodoVertice vertOr, vertDes;
		boolean existenTodos = true;

		vertOr = this.buscarNodoVertice(inicio, origen);
		vertDes = this.buscarNodoVertice(inicio, destino);
		// TODO Modificado
		for (Ciudad c : obligatorios) {
			existenTodos &= (this.buscarNodoVertice(inicio, c)) != null;
			if (!existenTodos) {
				break;
			}
		}
		// --
		Lista lsVisitados = new Lista();
		Camino camino = new Camino();
		// Se usa un arreglo por tema de referencia, para poder modificarlo segÃºn mi
		// conveniencia.
		int[] distanciaMinima = { Integer.MAX_VALUE };
		if (vertOr != null && vertDes != null && existenTodos) {
			System.out.println("Hace llamada al metodo privado");
			camino.setListaDeNodos(caminoMenorDistanciaAux(vertOr, destino, lsVisitados, 0, camino.getListaDeNodos(),
					distanciaMinima, obligatorios));
			camino.setDistancia(distanciaMinima[0]);
		}
		return camino;
	}

	private Lista caminoMenorDistanciaAux(NodoVertice n, Object dest, Lista lsVisitados, int distActual, Lista lsCamino,
			int[] distMin, LinkedList<Ciudad> obligatorios) {
		//
		lsVisitados.insertar(n.getElem(), lsVisitados.longitud() + 1);
		if (n.getElem().equals(dest) && lsVisitados.incluye(obligatorios)) {
			/**
			 * Como "lsVisitados" va a sufrir modificaciones se decide clonarla, porque si
			 * sino se le asigna va a sufrir modificaciones, "lsCamino" va a sufrir las
			 * mismas modificaciones (referencia).
			 */
			lsCamino = lsVisitados.clone();
			distMin[0] = distActual;

		} else {
			NodoAdyacente ady = n.getAdyacente();
			while (ady != null) {
				distActual += (int) ady.getEtiqueta();
				if (distActual < distMin[0]) {
					if (lsVisitados.localizar(ady.getVerticeAdy().getElem()) < 0) {
						lsCamino = caminoMenorDistanciaAux(ady.getVerticeAdy(), dest, lsVisitados, distActual, lsCamino,
								distMin, obligatorios);
					}
				}
				// Se resta porque voy a ir por otro camino,y este no continen al adyacente
				// anterior.
				distActual -= (int) ady.getEtiqueta();
				ady = ady.getSigAdyacente();
			}
		}
		lsVisitados.eliminar(lsVisitados.longitud());

		return lsCamino;
	}


	public Camino obtenerCaminoCortoConPasosObligados(Ciudad origen, Ciudad destino, HashSet<Ciudad> obligatorias){
		NodoVertice nodoOrigen,nodoFinal,nodoV;
		NodoAdyacente nodoA;
		HashMap<HashSet<Ciudad>,Integer> matrizAdyacencia;
		HashMap<Ciudad,HashMap<HashSet<Ciudad>,Camino>> matrizCaminos;
		Ciudad c1,c2;
		HashSet<Ciudad> ciudades, set;
		int cantCaminos;
		Camino respuesta;
		
		nodoOrigen=this.buscarNodoVertice(this.inicio, origen);
		nodoFinal=this.buscarNodoVertice(this.inicio, destino);
		ciudades=new HashSet<Ciudad>();
		respuesta=new Camino();
		respuesta.agregarCiudad(origen, -1);
		respuesta.agregarCiudad(destino, 0);
		//constuimos la matriz inicial;

		if(nodoOrigen!=null && nodoFinal!=null){
			//Obtenemos todas las ciudades
			ciudades.add(((Ciudad)nodoOrigen.getElem()));
			nodoV=this.inicio;
			while(nodoV!=null){
				if(nodoV!=nodoOrigen){
					ciudades.add(((Ciudad)nodoV.getElem()));
				}
				nodoV=nodoV.getSigVertice();
			}

			System.out.println("Ciudades obtenidas en conjunto");
			
			cantCaminos=(int) Math.pow(2, ciudades.size()-1);
			matrizCaminos=new HashMap<Ciudad,HashMap<HashSet<Ciudad>,Camino>>(ciudades.size());
			matrizAdyacencia=new HashMap<HashSet<Ciudad>,Integer>(ciudades.size()*ciudades.size());
			nodoV=this.inicio;

			//Cargamos la matriz de adyacencia
			while(nodoV!=null){
				nodoA=nodoV.getAdyacente();
				while(nodoA!=null){
					c1=(Ciudad)nodoV.getElem();
					c2=(Ciudad) nodoA.getVerticeAdy().getElem();
					set=new HashSet<Ciudad>();
					set.add(c1);
					set.add(c2);
					matrizAdyacencia.put(set, (int)nodoA.getEtiqueta());

					//Creamos HashMaps de caminos
					matrizCaminos.put(c1, new HashMap<HashSet<Ciudad>,Camino>(cantCaminos));
					nodoA=nodoA.getSigAdyacente();
				}
				nodoV=nodoV.getSigVertice();
			}

			//Tratamos de resolver
			respuesta=this.obtenerCaminoCortoConPasosObligados(matrizCaminos, matrizAdyacencia, ciudades, nodoOrigen, nodoFinal,obligatorias);
		}

		return respuesta;

	}


	public Camino obtenerCaminoCortoConPasosObligados(HashMap<Ciudad,HashMap<HashSet<Ciudad>,Camino>> matrizCaminos, HashMap<HashSet<Ciudad>,Integer> matrizDeAdyacencia, HashSet<Ciudad> ciudadesAConsiderar, NodoVertice nodoOrigen, NodoVertice destino, HashSet<Ciudad> obligatorias){
		//Establecer tamaño de sets
		//matriz caminos hace de funcion C x P(C) ---> Camino donde C son ciudades, y P(N) partes de ciudades, el objetivo es llenarla
		//matriz de Adyacencia contiene las distancias de los nodos adyacentes a cada nodo (en este caso ciudad), es del tipo C x C ---> Integer
		//Ciudades a considerar es el conjunto de todas las ciudades
		
				
		Camino camino, caminoConsultado, caminoRespuesta, caminoAux2;
		Ciudad ciudadOrigen, ciudadDestino;
		HashSet<Ciudad> keyAdy, keyAdyRes;
		HashSet<Ciudad> keyCaminos;
		HashSet<Ciudad> setSimpleAux, setAux, setAux2;
		HashSet<HashSet<Ciudad>> setsActuales; //La idea es formar 2^N sets, donde N es el número de nodos del grafo
		HashSet<HashSet<Ciudad>> setsARecorrer;
		boolean analizarRespuesta;
		int distCaminoAux2;

		ciudadDestino=(Ciudad) destino.getElem();
		ciudadOrigen=(Ciudad)nodoOrigen.getElem();
		setsActuales=new HashSet<HashSet<Ciudad>> (); //Comenzaremos con 0 sets
		caminoRespuesta=new Camino();
		caminoRespuesta.setDistancia(Integer.MAX_VALUE);
		caminoRespuesta.agregarCiudad(ciudadOrigen, 0);
		caminoRespuesta.agregarCiudad(ciudadOrigen, 0);

		//Caso inicial, llenamos la matriz de caminos con los nodos individuales yendo al destino, su distancia sera la que indique la matriz de adyacencia
		keyCaminos=new HashSet<Ciudad>();//conjunto vacio
		for (Ciudad c: ciudadesAConsiderar){
			if(!c.equals(ciudadDestino)){
				keyAdy=new HashSet<Ciudad>();
				keyAdy.add(c);
				keyAdy.add(ciudadDestino);
				
				//Para Debug
				//System.out.println(c.getCiudad()+" - "+ciudadOrigen.getCiudad()+": "+matrizDeAdyacencia.get(keyAdy));

				camino=new Camino();
				camino.agregarCiudad(ciudadDestino, 0);
				camino.agregarCiudad(c, matrizDeAdyacencia.get(keyAdy));
				System.out.println(camino.toString());
					
				matrizCaminos.get(c).put(keyCaminos, camino);
				setsActuales.add(keyCaminos);
			}
		}

		setsActuales.add(new HashSet<Ciudad>()); //Añado conjunto vacio

		do{
			//generamos nuevos sets a partir de los sets anteriores
			setsARecorrer=new HashSet<HashSet<Ciudad>>();
			for(HashSet<Ciudad> s: setsActuales){
				//Trato de generar nuevos sets
				for(Ciudad c: ciudadesAConsiderar){
					if(!c.equals(ciudadOrigen) && !c.equals(ciudadDestino) && !s.contains(c)){
						setSimpleAux=(HashSet<Ciudad>) s.clone();
						setSimpleAux.add(c);
						setsARecorrer.add(setSimpleAux);
					}
				}
			}

			//Para debug
			//System.out.println(this.mostrarMatrizDeCaminos(setsActuales, matrizCaminos, ciudadesAConsiderar, ciudadOrigen));
			//System.out.println("");
			
			setsActuales=setsARecorrer;

			//Para debug
			//System.out.print("Apretar ENTER para continuar");
			//String cont=TecladoIn.readLine();

			for(HashSet<Ciudad> s: setsActuales){
				//Trato de generar nuevos sets
				for(Ciudad c: ciudadesAConsiderar){
					if(!s.contains(c)){
						setAux=(HashSet<Ciudad>)s.clone();
						camino=new Camino();
						camino.setDistancia(Integer.MAX_VALUE);
						matrizCaminos.get(c).put(setAux, camino);
						for(Ciudad ciudadEnSet: s){
							setAux.remove(ciudadEnSet);

							keyAdy=new HashSet<Ciudad>();
							keyAdy.add(c);
							keyAdy.add(ciudadEnSet);
								
							caminoConsultado=matrizCaminos.get(ciudadEnSet).get(setAux);
							camino=new Camino();
							camino.setListaDeNodos(caminoConsultado.getListaDeNodos().clone()); //Clono para evitar problemas con referencias
							camino.setDistancia(caminoConsultado.getDistancia()+matrizDeAdyacencia.get(keyAdy));
							camino.agregarCiudad(c, 0);

							setAux.add(ciudadEnSet);
							caminoConsultado=matrizCaminos.get(c).get(setAux);

							if(caminoConsultado.getDistancia()>camino.getDistancia()){
								//me quedo con el camino mínimo
								matrizCaminos.get(c).put(setAux,camino.clone());
							}

							analizarRespuesta=(camino.getDistancia()<caminoRespuesta.getDistancia());
							for (Ciudad obli : obligatorias) {
								analizarRespuesta=(camino.getListaDeNodos().localizar(obli)!=-1) && analizarRespuesta;
								if(!analizarRespuesta){
									break;
								}
							}

							if(analizarRespuesta && !obligatorias.contains(c)){
								for (Ciudad c2: setAux){
									//System.out.println("Analizo respuesta para set: "+this.mostrarClaveConjuntoCiudades(setAux) +" y ciudad "+c.toString()+" con camino " +matrizCaminos.get(c).get(setAux).toString());
									setAux2=(HashSet<Ciudad>)setAux.clone();
									setAux2.remove(c2);
									keyAdyRes=new HashSet<Ciudad>();
									keyAdyRes.add(ciudadOrigen);
									keyAdyRes.add(c2);
									caminoAux2=matrizCaminos.get(c2).get(setAux2);
									distCaminoAux2=matrizDeAdyacencia.get(keyAdyRes)+caminoAux2.getDistancia();
									if(distCaminoAux2<caminoRespuesta.getDistancia()){
										caminoRespuesta=caminoAux2.clone();
										//caminoRespuesta.agregarCiudad(c2, 0);
										caminoRespuesta.agregarCiudad(ciudadOrigen, 0);
										caminoRespuesta.setDistancia(distCaminoAux2);
									}
								}
							}else if (analizarRespuesta && obligatorias.contains(c)){
								//System.out.println("Analizo respuesta para set: "+this.mostrarClaveConjuntoCiudades(setAux) +" y ciudad "+c.toString()+" con camino " +matrizCaminos.get(c).get(setAux).toString());
								caminoAux2=camino.clone();
								keyAdyRes=new HashSet<Ciudad>();
								keyAdyRes.add(ciudadOrigen);
								keyAdyRes.add(c);
								distCaminoAux2=matrizDeAdyacencia.get(keyAdyRes)+caminoAux2.getDistancia();
								if(distCaminoAux2<caminoRespuesta.getDistancia()){
									caminoRespuesta=caminoAux2.clone();
									//caminoRespuesta.agregarCiudad(c2, 0);
									caminoRespuesta.agregarCiudad(ciudadOrigen, 0);
									caminoRespuesta.setDistancia(distCaminoAux2);
								}
							}
						}
					}
				}
			}
			

		}while(setsActuales.size()!=1);

		return caminoRespuesta;
	}



	public Camino obtenerCicloDeHamiltonMenorAPartirDeOrigen(Ciudad origen){
		NodoVertice nodoOrigen,nodoFinal,nodoV;
		NodoAdyacente nodoA;
		HashMap<HashSet<Ciudad>,Integer> matrizAdyacencia;
		HashMap<Ciudad,HashMap<HashSet<Ciudad>,Camino>> matrizCaminos;
		Ciudad c1,c2;
		HashSet<Ciudad> ciudades, set;
		int cantCaminos;
		Camino respuesta;
		
		nodoOrigen=this.buscarNodoVertice(this.inicio, origen);
		nodoFinal=this.buscarNodoVertice(this.inicio, origen);
		ciudades=new HashSet<Ciudad>();
		respuesta=new Camino();
		respuesta.agregarCiudad(origen, -1);
		respuesta.agregarCiudad(origen, 0);
		//constuimos la matriz inicial;

		if(nodoOrigen!=null && nodoFinal!=null){
			System.out.println("Nodo inicial y destinos encontrados");
			//Obtenemos todas las ciudades
			ciudades.add(((Ciudad)nodoOrigen.getElem()));
			nodoV=this.inicio;
			while(nodoV!=null){
				if(nodoV!=nodoOrigen){
					ciudades.add(((Ciudad)nodoV.getElem()));
				}
				nodoV=nodoV.getSigVertice();
			}

			System.out.println("Ciudades obtenidas en conjunto");
			
			cantCaminos=(int) Math.pow(2, ciudades.size()-1);
			matrizCaminos=new HashMap<Ciudad,HashMap<HashSet<Ciudad>,Camino>>(ciudades.size());
			matrizAdyacencia=new HashMap<HashSet<Ciudad>,Integer>(ciudades.size()*ciudades.size());
			nodoV=this.inicio;

			//Cargamos la matriz de adyacencia
			while(nodoV!=null){
				nodoA=nodoV.getAdyacente();
				while(nodoA!=null){
					c1=(Ciudad)nodoV.getElem();
					c2=(Ciudad) nodoA.getVerticeAdy().getElem();
					set=new HashSet<Ciudad>();
					set.add(c1);
					set.add(c2);
					matrizAdyacencia.put(set, (int)nodoA.getEtiqueta());

					//Creamos HashMaps de caminos
					matrizCaminos.put(c1, new HashMap<HashSet<Ciudad>,Camino>(cantCaminos));
					nodoA=nodoA.getSigAdyacente();
				}
				nodoV=nodoV.getSigVertice();
			}

			System.out.println(mostrarAdyacencia(matrizAdyacencia));
			//Tratamos de resolver
			respuesta=this.obtenerCicloDeHamiltonMenorAPartirDeOrigen(matrizCaminos, matrizAdyacencia, ciudades, nodoOrigen);
		}

		return respuesta;

	}

	

	public Camino obtenerCicloDeHamiltonMenorAPartirDeOrigen(HashMap<Ciudad,HashMap<HashSet<Ciudad>,Camino>> matrizCaminos, HashMap<HashSet<Ciudad>,Integer> matrizDeAdyacencia, HashSet<Ciudad> ciudadesAConsiderar, NodoVertice nodoOrigen){
		//Establecer tamaño de sets
		//matriz caminos hace de funcion C x P(C) ---> Camino donde C son ciudades, y P(N) partes de ciudades, el objetivo es llenarla
		//matriz de Adyacencia contiene las distancias de los nodos adyacentes a cada nodo (en este caso ciudad), es del tipo C x C ---> Integer
		//Ciudades a considerar es el conjunto de todas las ciudades
		
				
		Camino camino, caminoConsultado, caminoRespuesta;
		Ciudad ciudadOrigen;
		HashSet<Ciudad> keyAdy;
		HashSet<Ciudad> keyCaminos;
		HashSet<Ciudad> setSimpleAux, setAux;
		HashSet<HashSet<Ciudad>> setsActuales; //La idea es formar 2^N sets, donde N es el número de nodos del grafo
		HashSet<HashSet<Ciudad>> setsARecorrer;

		ciudadOrigen=(Ciudad)nodoOrigen.getElem();
		setsActuales=new HashSet<HashSet<Ciudad>> (); //Comenzaremos con 0 sets
		caminoRespuesta=new Camino();
		caminoRespuesta.setDistancia(Integer.MAX_VALUE);
		caminoRespuesta.agregarCiudad(ciudadOrigen, 0);
		caminoRespuesta.agregarCiudad(ciudadOrigen, 0);

		//Caso inicial, llenamos la matriz de caminos con los nodos individuales yendo al destino, su distancia sera la que indique la matriz de adyacencia
		keyCaminos=new HashSet<Ciudad>();//conjunto vacio
		for (Ciudad c: ciudadesAConsiderar){
			if(!c.equals(ciudadOrigen)){
				keyAdy=new HashSet<Ciudad>();
				keyAdy.add(c);
				keyAdy.add(ciudadOrigen);

				//System.out.println(c.getCiudad()+" - "+ciudadOrigen.getCiudad()+": "+matrizDeAdyacencia.get(keyAdy));

				camino=new Camino();
				camino.agregarCiudad(ciudadOrigen, 0);
				camino.agregarCiudad(c, matrizDeAdyacencia.get(keyAdy));
				System.out.println(camino.toString());
					
				matrizCaminos.get(c).put(keyCaminos, camino);
				setsActuales.add(keyCaminos);
			}
		}

		setsActuales.add(new HashSet<Ciudad>()); //Añado ocnjunto vacio

		System.out.println("Termino caso base");

		do{
			//generamos nuevos sets a partir de los sets anteriores
			setsARecorrer=new HashSet<HashSet<Ciudad>>();
			for(HashSet<Ciudad> s: setsActuales){
				//Trato de generar nuevos sets
				for(Ciudad c: ciudadesAConsiderar){
					if(!c.equals(ciudadOrigen) && !s.contains(c)){
						setSimpleAux=(HashSet<Ciudad>) s.clone();
						setSimpleAux.add(c);
						setsARecorrer.add(setSimpleAux);
					}
				}
			}

			System.out.println(this.mostrarMatrizDeCaminos(setsActuales, matrizCaminos, ciudadesAConsiderar, ciudadOrigen));
			System.out.println("");
			
			setsActuales=setsARecorrer;
			//System.out.println(mostrarClaveConjuntoDeConjuntoCiudades(setsActuales));


			System.out.print("Apretar ENTER para continuar");
			String cont=TecladoIn.readLine();

			for(HashSet<Ciudad> s: setsActuales){
				//Trato de generar nuevos sets
				for(Ciudad c: ciudadesAConsiderar){
					if(!s.contains(c)){
						setAux=(HashSet<Ciudad>)s.clone();
						camino=new Camino();
						camino.setDistancia(Integer.MAX_VALUE);
						matrizCaminos.get(c).put(setAux, camino);
						for(Ciudad ciudadEnSet: s){
							setAux.remove(ciudadEnSet);

							keyAdy=new HashSet<Ciudad>();
							keyAdy.add(c);
							keyAdy.add(ciudadEnSet);
								
							caminoConsultado=matrizCaminos.get(ciudadEnSet).get(setAux);
							camino=new Camino();
							camino.setListaDeNodos(caminoConsultado.getListaDeNodos().clone()); //Clono para evitar problemas con referencias
							camino.setDistancia(caminoConsultado.getDistancia()+matrizDeAdyacencia.get(keyAdy));
							camino.agregarCiudad(c, 0);

							setAux.add(ciudadEnSet);
							caminoConsultado=matrizCaminos.get(c).get(setAux);

							if(caminoConsultado.getDistancia()>camino.getDistancia()){
								//me quedo con el camino mínimo
								matrizCaminos.get(c).put(setAux,camino.clone());
							}
						}
					}
				}
			}
		}while(setsActuales.size()!=1);

		for (HashSet<Ciudad> hashSet : setsActuales) {
			for (Ciudad c : hashSet) {
				keyAdy=new HashSet<Ciudad>();
				keyAdy.add(ciudadOrigen);
				keyAdy.add(c);

				setAux=(HashSet<Ciudad>)hashSet.clone();
				setAux.remove(c);
				camino=matrizCaminos.get(c).get(setAux).clone();

				camino.agregarCiudad(ciudadOrigen, matrizDeAdyacencia.get(keyAdy));

				if(caminoRespuesta.getDistancia()>camino.getDistancia()){
					caminoRespuesta=camino;
				}
			}
			
		}

		return caminoRespuesta;
	}

	public String mostrarAdyacencia(HashMap<HashSet<Ciudad>,Integer> matrizAdyacencia){
		String sal,claveS;
		sal="";
		for (HashSet<Ciudad> clave : matrizAdyacencia.keySet()) {
			claveS= "[";
			for (Ciudad ciudad : clave) {
				claveS+=ciudad.getCiudad()+", ";
			}
			claveS=claveS.substring(0, claveS.length()-2);
			claveS+="]";
			sal+= claveS+": "+ matrizAdyacencia.get(clave)+"\n";
		}
		return sal;
	}

	public String obtenerTablaCaminos(){
		return "";
	}

	public String mostrarClaveConjuntoCiudades(HashSet<Ciudad> clave){
		String sal;
		sal="";

		if(clave==null){
			sal="No hay conjunto";
		}else if (clave.size()==0){
			sal="[NULL]";
		}else{
			sal="[";
			for (Ciudad ciudad : clave) {
				sal+=ciudad.getCiudad()+", ";			
			}
			sal=sal.substring(0,sal.length()-2);
			sal+="]";
		}
		return sal;
	}


	public String mostrarClaveConjuntoDeConjuntoCiudades(HashSet<HashSet<Ciudad>> conjuntoClaves){
		String sal;
		sal="[";
		for (HashSet<Ciudad> hashSet : conjuntoClaves) {
			sal+=mostrarClaveConjuntoCiudades(hashSet);
			sal+="; ";
		}
		if(sal.length()>2){
			sal=sal.substring(0,sal.length()-2);
		}
		sal+="]";
		return sal;
	}

	public String mostrarMatrizDeCaminos(HashSet<HashSet<Ciudad>> conjuntoClaves, HashMap<Ciudad,HashMap<HashSet<Ciudad>,Camino>> matrizCaminos, HashSet<Ciudad> ciudadesAConsiderar, Ciudad ciudadOrigen){
		String sal;
		sal="[\n";
		for (HashSet<Ciudad> hashSet : conjuntoClaves) {
			if(hashSet.size()!=0){

				for (Ciudad ciudad : ciudadesAConsiderar) {
					if(!hashSet.contains(ciudad) && !ciudad.equals(ciudadOrigen)){
						sal+="{"+ciudad +"; "+ mostrarClaveConjuntoCiudades(hashSet)+"}";
						sal+=": ";
						//System.out.println(sal);
						sal+= matrizCaminos.get(ciudad).get(hashSet).toString()+"\n";
						//System.out.println(sal);
					}
				}
			}
			
		}
		if(sal.length()>2){
			sal=sal.substring(0,sal.length()-2);
		}
		sal+="]";
		return sal;

	}

}
