import java.io.*;
import java.util.*;
import static java.lang.Math.random;
// Chriss Brito
// Daniel Salazar
// Gabriel Zuleta

public class App {
	
	public static void main(String[] args) throws IOException{
		
		// jugador
		
		String [] usuario= new String [10000];
		String [] contraseña= new String [10000];
		int [] hp= new int [10000];
		int [] atk= new int [10000];
		int [] def= new int [10000];
		int [] vel= new int [10000];
		int [] numHechizos= new int [10000];
		int [] exp= new int [10000];
		
		//hechizos
		
		String [] hechizos= new String [10000];
		int [] poder= new int [10000];
		
		//enemigo
		
		String [] nombreEn= new String [10000];
		int [] hpEn= new int [10000];
		int [] atkEn= new int [10000];
		String [] clase= new String [10000];
		int [] velEn= new int [10000];
		
		//arreglos
		
		int cantJugadores= leerJugadores(usuario,contraseña,hp,atk,def,vel,numHechizos,exp);
		int cantHechizos= leerHechizos(hechizos,poder);
		int cantEnemigos= leerEnemigos(nombreEn,hpEn,atkEn,clase,velEn);
		int [] enyhec= new int[3];
		enyhec[0]=cantEnemigos; enyhec[1]=cantHechizos; enyhec[2]=cantJugadores;
		
		//hechizosJugadores
		
		boolean[][] hecJugadores=new boolean[10000][10000];
		leerHechizosJugadores(hecJugadores,hechizos,usuario,cantJugadores,cantHechizos);
		
		//
		
		int cerrar=-1;
		while (cerrar!=0) {
			cerrar=iniciarSesion(usuario,contraseña,cantJugadores,cantHechizos,cantEnemigos,hp,atk,def,vel,numHechizos,exp,hechizos,poder,nombreEn,hpEn,atkEn,clase,velEn,hecJugadores,enyhec);
			if (cerrar==2) {
				cantJugadores++;
			}
			if (cerrar==3) {
				cantEnemigos=enyhec[0];
				cantHechizos=enyhec[1];
				cantJugadores=enyhec[2];
			}
			if (cerrar!=0) {
				System.out.println("");System.out.println("si desea salir y cerrar el programa escriba 'exit'");
			}
		}
		cerrarJugadores(cantJugadores,usuario,contraseña,hp,atk,def,vel,numHechizos,exp);
		cerrarHechizos(cantHechizos,hechizos,poder);
		cerrarHechizosJugadores(cantJugadores,cantHechizos,usuario,hechizos,hecJugadores);
		cerrarEnemigos(cantEnemigos,nombreEn,hpEn,atkEn,clase,velEn);
	}
	
	/**
	 * with this function the user or ADMIN will be able to log in in order to interact with the system
	 * @param sistema - system
	 */
	public static int iniciarSesion(String[] usuario,String[] contraseña,int cantJugadores,int cantHechizos,int cantEnemigos,int[] hp,int[] atk,int[] def,int[] vel,int[] numHechizos,int[] exp,String[]hechizos,int[]poder,String[]nombreEn,int[]hpEn,int[]atkEn,String[]clase,int[]velEn,boolean[][]hecJugadores,int[]enyhec) {
	        Scanner leer = new Scanner(System.in);
	        boolean cerrar = false; boolean admin=false;
	        System.out.println("-       -INICIO SESION-       -");
	        while (cerrar==false) {
	        	System.out.println("Usuario: "); 
	        	String user = leer.nextLine();
	        	if (user.equals("exit")) {
	        		System.out.println("-       Cerrando..       -");
	        		return 0;
	        	}
	        	else {
	        		System.out.println("Contraseña: "); 
	        		String pass = leer.nextLine();
	        		if ((user.equals("Admin")) && (pass.equals("Patata19"))) {
	        			System.out.println("-       -Menú Admin-       -");
	        			menuAdmin(cantJugadores,cantEnemigos,cantHechizos,usuario,contraseña,hp,atk,def,vel,numHechizos,exp,hechizos,poder,nombreEn,hpEn,atkEn,clase,velEn,hecJugadores,enyhec);
	        			cerrar=true;
	        			admin=true;
	        		}
	        		else {
	        			int indiceUser= buscarIndex(cantJugadores,usuario,user);
	        			int indicePass= buscarIndexContra(cantJugadores,contraseña,pass,indiceUser,usuario);
	        			if (indiceUser==indicePass){
	        				if (indiceUser!=-1 && indicePass!=-1) { //
	        					System.out.println("-      -Menú Usuario-      -");
	        					menuUsuario(indiceUser,cantJugadores,cantEnemigos,cantHechizos,usuario,hp,atk,def,vel,numHechizos,exp,hechizos,poder,nombreEn,hpEn,atkEn,clase,velEn,hecJugadores);
	        					cerrar=true;
	        				}
	        				else {
	        					System.out.println("Usuario no registrado");System.out.println("Si se desea registrar ingrese '1'");
	        					String respuesta=leer.nextLine();
	        					if (respuesta.equals("1")) {
	        						System.out.println("ingrese Nombre:"); String newUser=leer.nextLine();
	        						System.out.println("ingrese Contraseña:"); String newPass=leer.nextLine();
	        						usuario[cantJugadores]= newUser;
	        						contraseña[cantJugadores]= newPass;
	        						hp[cantJugadores]=10;
	        						atk[cantJugadores]=1;
	        						def[cantJugadores]=1;
	        						vel[cantJugadores]=1;
	        						numHechizos[cantJugadores]=0;
	        						exp[cantJugadores]=0;
	        						return 2;
	        					}
	        					else{
	        						cerrar=true;
	        					}
	        				}
	        			}
	        			else {
	        				System.out.println("Hubo un error. Ingrese datos nuevamente");
							cerrar=true;
	        			}
	        		}
	        	}
	        }
	        if (admin=true) {
	        	return 3;
	        }
	        else{
	        	return 1; 
	        }
	 }      		

	/**
	 * with this function the user will be able to interact with the system
	 * @param sistema - system
	 */ 
	public static void menuUsuario (int indiceUser,int cantJugadores,int cantEnemigos,int cantHechizos,String[]usuario,int[]hp,int[]atk,int[]def,int[]vel,int[]numHechizos,int[]exp,String[]hechizos,int[]poder,String[]nombreEn,int[]hpEn,int[]atkEn,String[]clase,int[]velEn,boolean[][]hecJugadores) {
		int salida=-1;
		Scanner leer= new Scanner (System.in);
		String a; int pelea; int in; boolean resultado; boolean salir=false;
		while (salida!= 6) {
			System.out.println("Hola "+ usuario[indiceUser]+", que acción deseas realizar?");
			System.out.println("1)Luchar contra Enemigo");System.out.println("2)Aprender Hechizo");System.out.println("3)Ver Estadisticas de un Jugador");System.out.println("4)Ver Hechizos de un jugador");System.out.println("5)Ver Ranking");System.out.println("6)Salir");
			a=leer.nextLine();salida=Integer.parseInt(a);
			if (salida==1) {
                System.out.println("¿Que tipo de Lucha desea?");System.out.println("1) PvE");System.out.println("2) PvP");
                pelea = Integer.parseInt(leer.nextLine()); salir=false;
                while (salir==false) {
                	if (pelea == 1){
                		System.out.println("Buscando enemigo para lucha PvE..."+"\n");
                		in = eleccionEnemigo(clase,cantEnemigos,nombreEn);
                		resultado = pve(usuario,hp,atk,def,vel,numHechizos,exp,nombreEn,hpEn,atkEn,clase,velEn,in,indiceUser,hechizos,poder,cantHechizos,hecJugadores);
                		distribucionStats(resultado,hp,atk,def,vel,indiceUser,in,clase);
                		salir=true;
                	}
                	else if (pelea == 2){
                		System.out.println("Buscando enemigo para lucha PvP");
                		in = eleccionEnemigoPVP(indiceUser,usuario,cantJugadores);
                		resultado = pvp(usuario,hp,atk,def,vel,numHechizos,exp,indiceUser,in,hechizos,poder,cantHechizos,hecJugadores);
                		if (resultado == true){
                            exp[indiceUser] = exp[indiceUser] + 250;
                        }
                		salir=true;
                	}
                	else {
                		System.out.println("opción inválida, intente nuevamente");
                		System.out.println("¿Que tipo de Lucha desea?");System.out.println("1) PvE");System.out.println("2) PvP");
                        pelea = Integer.parseInt(leer.nextLine()); 
                	}
                }
			}
			else if (salida==2) {
				aprenderHec(indiceUser,cantHechizos,cantJugadores,numHechizos,exp,hechizos,hecJugadores);
			}
			else if (salida==3) {
				verStats(cantJugadores,usuario,hp,atk,def,vel,numHechizos);
			}
			else if (salida==4) {
				verStatsHec(cantJugadores,usuario,numHechizos,hechizos,cantHechizos,hecJugadores);
			}
			else if (salida==5) {
				ranking(cantJugadores,usuario,exp);
			}
			else if (salida==6) {
				System.out.println("Cerrando sesión...");
			}
			else {
				System.out.println("numero no valido, intente nuevamente");System.out.println("");
			}
		}
	}
	
	/**
	 * with this function the admin will be able to interact with the system
	 * @param sistema - system
	 */
	public static void menuAdmin (int cantJugadores,int cantEnemigos,int cantHechizos,String[]usuario,String[]contraseña,int[]hp,int[]atk,int[]def,int[]vel,int[]numHechizos,int[]exp,String[]hechizos,int[]poder,String[]nombreEn,int[]hpEn,int[]atkEn,String[]clase,int[]velEn,boolean[][]hecJugadores,int[]enyhec) {
		int salida=-1;
		Scanner leer= new Scanner (System.in);
		int newen;int newhec; int newuser;
		while (salida!= 5) {
			System.out.println("Hola todopoderoso admin, que acción deseas realizar?");System.out.println("1) Eliminar un jugador");System.out.println("2) Agregar enemigos");System.out.println("3) Agregar Hechizos");System.out.println("4) Ver estadisticas");System.out.println("5) salir");
			String a=leer.nextLine();salida=Integer.parseInt(a);
			if (salida==1) {
				newuser=eliminarJugador(cantJugadores,cantHechizos,usuario,contraseña,hp,atk,def,vel,numHechizos,exp,hecJugadores);
				enyhec[2]=newuser;
				cantJugadores=enyhec[2];
			}
			else if (salida==2) {
				newen =agregarEn(nombreEn,hpEn,atkEn,clase,velEn,cantEnemigos);
				enyhec[0]+=newen;
				cantEnemigos=enyhec[0];
			}
			else if (salida==3) {
				newhec= agregarHec(hechizos,poder,cantHechizos);
				enyhec[1]+=newhec;
				cantHechizos=enyhec[1];
			}
			else if (salida==4) {
				verStatsAdmin(cantJugadores,usuario,hp,atk,def,vel,numHechizos,exp);
			}
			else if (salida==5) {
				System.out.println("Cerrando sesión...");
			}
			else {
				System.out.println("numero no valido, intente nuevamente");System.out.println("");
			}
		}
	}
	
	/**
	 * A function that reads the file "Jugadores.txt" and saves the data in the system through its methods
	 * @param sistema - system
	 * @return contJugadores - number of Players entered
	 * @throws IOException - An exception
	 */
	public static int leerJugadores(String[] usuario,String[] contraseña,int [] hp,int [] atk,int [] def,int [] vel,int [] numHechizos,int [] exp) throws IOException {
        File arch = new File("Jugadores.txt");
        BufferedReader jugador = new BufferedReader(new FileReader(arch));
        int cant = 0;
        String line;
        while ((line = jugador.readLine()) != null){
            String[] part = line.split(",");
            usuario[cant] = part[0];
            contraseña[cant] = part[1];
            int numHp= Integer.parseInt(part[2]); hp[cant]=numHp;
            int numAtk= Integer.parseInt(part[3]); atk[cant]=numAtk;
            int numDef= Integer.parseInt(part[4]); def[cant]=numDef;
            int numVel= Integer.parseInt(part[5]); vel[cant]=numVel;
            int numHec= Integer.parseInt(part[6]); numHechizos[cant]=numHec;
            int numExp= Integer.parseInt(part[7]); exp[cant]=numExp;
            cant++;
        }
        jugador.close();
        return cant;
	}
	
	/**
	 * A function that reads the file "Hechizos.txt" and saves the data in the system through its methods
	 * @param sistema - system
	 * @return contHechizos - number of spells entered
	 * @throws IOException - An exception
	 */
	public static int leerHechizos(String[] hechizo,int [] poder) throws IOException {
        File arch = new File("Hechizos.txt");
        BufferedReader hec = new BufferedReader(new FileReader(arch));
        int cant = 0;
        String line;
        while ((line = hec.readLine()) != null){
            String[] part = line.split(",");
            hechizo[cant] = part[0];
            int num= Integer.parseInt(part[1]); poder[cant]=num;
            cant++;
        }
        hec.close();
        return cant;
	}
	
	/**
	 * A function that reads the file "Enemigos.txt" and saves the data in the system through its methods
	 * @param sistema - system
	 * @return contEnemigos - number of enemies entered
	 * @throws IOException - An exception
	 */
	public static int leerEnemigos(String[]nombreEn, int [] hpEn, int[]atkEn, String[]clase, int [] velEn) throws IOException {
        File arch = new File("Enemigos.txt");
        BufferedReader en = new BufferedReader(new FileReader(arch));
        int cant = 0;
        String line;
        while ((line = en.readLine()) != null){
            String[] part = line.split(",");
            nombreEn[cant] = part[0];
            int numHp= Integer.parseInt(part[1]); hpEn[cant]=numHp;
            int numAtk= Integer.parseInt(part[2]); atkEn[cant]=numAtk;
            clase[cant]=part[3];
            int numVel= Integer.parseInt(part[4]); velEn[cant]=numVel;
            cant++;
        }
        en.close();
        return cant;
	}
	
	/**
	 * A function that reads the file "HechizosJugadores.txt" and saves the data in the system through its methods
	 * @param sistema - system
	 * @throws IOException - An exception
	 */
	public static void leerHechizosJugadores(boolean[][] hecJugadores,String [] hechizos,String [] usuario,int cantJugadores, int cantHechizos) throws IOException{
		File arch = new File("HechizosJugadores.txt");
        BufferedReader hecJugador = new BufferedReader(new FileReader(arch));
        String line;
        int cant=0;
        String jugador;String hec;
        while ((line = hecJugador.readLine()) != null){
            String[] part = line.split(",");
            jugador = part[0];
            hec=part[1];
            for (int i=0; i<=cantJugadores-1;i++) {
            	if (jugador.equals(usuario[i])) {
            		for (int j=0;j<=cantHechizos-1;j++) {
            			if (hec.equals(hechizos[j])) {
            				hecJugadores[i][j]= true;
            			}
            		}
            	}
            }
        }
        hecJugador.close();
	}
	
    public static void crearUsuario(String[] usuario,String[] clave,int tamaño,int[] hp,int[] atk,int[] def,int[] vel,int[] numHechizos,int[] exp) {
    	System.out.println("--------------------");
    	Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese su Nombre de Usuario: ");
        usuario[tamaño] = leer.nextLine();
        System.out.println("Ingrese su Contraseña: ");
        clave[tamaño] = leer.nextLine();
        hp[tamaño] = 10;
        atk[tamaño] = 1;
        def[tamaño] = 1;
        vel[tamaño] = 1;
        numHechizos[tamaño] = 0;
        exp[tamaño] = 0;
        
        tamaño++;
        leer.close();
        System.out.println("--------------------");
    }
	
    /**
	 * this will search the index of the element in a vector
	 * @param sistema - system
	 * @return i - index needed
	 * @return -1 - not found in the vector
	 */
    public static int buscarIndex(int cantidad, String [] lista,String clave) {
		int i;
		for(i=0; i<cantidad; i++) {
			if(lista[i].equals(clave)) {
				break;
			}	
		}
		if(i==cantidad) {
			return -1;
		}
		else {
			return i;
		}
	}
        
	/**
	 * this will search the index of the element in a vector
	 * @param sistema - system
	 * @return i - index needed
	 * @return -1 - not found in the vector
	 */
	public static int buscarIndexContra(int cantidad, String [] contraseña,String clave,int indiceUser, String[] usuario) {
		int i; int x=0;
		for(i=0; i<cantidad; i++) {
			if(indiceUser==-1) {
				indiceUser=0;
			}
			if(contraseña[i].equals(clave)&& i==indiceUser) {
				x=i;
				break;
			}
			if(contraseña[i].equals(clave)) {
				x=i;
			}
		}
		if(i==cantidad && x<=0) {
			return -1;
		}
		if(i==cantidad && x>0) {
			return x;
		}
		else {
			return x;
		}
	}

    //USER
	
	/**
	 * if the user chooses this option, he has to battle against a random enemy player
	 */
	public static boolean pvp(String[] usuario,int [] hp,int [] atk,int [] def,int [] vel,int [] numHechizos,int [] exp,int indice,int indiceEnemigo,String [] hechizos,int [] poder,int cantHechizos,boolean[][] hecJugadores) {
		System.out.println("--------------------");
		Scanner opcion = new Scanner(System.in);
            int accion;
            boolean condicional = false;
            boolean turno;
            boolean resultado = false; //False = derrota, true = Victoria
            int vidaJugador = hp[indice];
            int vidaEnemigo = hp[indiceEnemigo];
            int daño;
            int comprobadorhec;
            int elechech;
            if (vel[indice] < vel[indiceEnemigo]){
                turno = false;
            }
            else {
                turno = true;
            }
            while (condicional == false){
                if(turno == false){
                    System.out.println(usuario[indiceEnemigo]+" atacó");
                    daño = atk[indiceEnemigo] - def[indice];
                    if (daño < 0){
                        daño = 0;
                    }
                    else{
                        vidaJugador = vidaJugador - daño;
                    }
                    System.out.println(usuario[indice]+" ha recibido "+daño+" puntos de daño"+"\n"+"Tu vida restante es "+vidaJugador);
                    turno = true;
                }
                else if ((turno == true) && (vidaJugador > 0)){
                    while (turno == true){
                        System.out.print("Que desea hacer? "+usuario[indice]+"\n"+"1) Ataque Básico"+"\n"+"2)Lanzar Hechizo"+"\n"+"3)Huir"+"\n"+"---> ");
                        accion = Integer.parseInt(opcion.nextLine());
                        if(accion == 1){
                            System.out.println("Haz realizado un ataque básico");
                            daño = atk[indice] - def[indiceEnemigo];
                            if (daño < 0){
                                daño = 0;
                                vidaEnemigo = vidaEnemigo - daño;
                                turno = false;
                                System.out.println("Has realizado "+daño+" puntos de daño"+"\n"+"La vida restante de "+usuario[indiceEnemigo]+" es "+vidaEnemigo);
                            }
                            else{
                                vidaEnemigo = vidaEnemigo - daño;
                                turno = false;
                                if (vidaEnemigo < 0){
                                    vidaEnemigo = 0;
                                    System.out.println("Has realizado "+daño+" puntos de daño"+"\n"+"La vida restante de "+usuario[indiceEnemigo]+" es "+vidaEnemigo);
                                }
                                else{
                                    System.out.println("Has realizado "+daño+" puntos de daño"+"\n"+"La vida restante de "+usuario[indiceEnemigo]+" es "+vidaEnemigo);
                                }
                            }
                        }
                        else if (accion == 2){
                            comprobadorhec = 0;
                            for(int b = 0; b < cantHechizos;b++){//Comprobador de que tiene hechizos para lanzar
                                if(hecJugadores[b][indice] == false){
                                    comprobadorhec++;
                                }
                            }
                            if(comprobadorhec < cantHechizos){
                                System.out.println("¿Que hechizo desea usar?");
                                int[] hec = new int[numHechizos[indice]];
                                int coco = 0;
                                for(int jo = 0;jo < cantHechizos;jo++){
                                    if (hecJugadores[indice][jo] == true){
                                        System.out.println(coco+") Hechizo: "+hechizos[jo]);
                                        hec[coco] = jo;
                                        coco++;
                                    }
                                }
                                while (turno == true){
                                    elechech = Integer.parseInt(opcion.nextLine());
                                    if(hecJugadores[indice][hec[elechech]] == false){
                                        System.out.println("Incomprensible");
                                        turno = true;
                                    }
                                    else{
                                        System.out.println("Has usado "+hechizos[hec[elechech]]);
                                        daño = poder[hec[elechech]] - def[indiceEnemigo];
                                        if (daño < 0){
                                            daño = 0;
                                            vidaEnemigo = vidaEnemigo - daño;
                                            turno = false;
                                            System.out.println("Has realizado "+daño+" puntos de daño"+"\n"+"La vida restante de "+usuario[indiceEnemigo]+" es "+vidaEnemigo);
                                        }
                                        else{
                                            vidaEnemigo = vidaEnemigo - daño;
                                            turno = false;
                                            if (vidaEnemigo < 0){
                                                vidaEnemigo = 0;
                                                System.out.println("Has realizado "+daño+" puntos de daño"+"\n"+"La vida restante de "+usuario[indiceEnemigo]+" es "+vidaEnemigo);
                                            }
                                            else{
                                                System.out.println("Has realizado "+daño+" puntos de daño"+"\n"+"La vida restante de "+usuario[indiceEnemigo]+" es "+vidaEnemigo);
                                            }
                                        }
                                    }
                                }
                            }
                            else{
                                System.out.println("Sin hechizos disponibles");
                                turno = true;
                            }
                        }
                        else if (accion == 3){
                            System.out.println("Te has rendido, a ganado :"+usuario[indiceEnemigo]);
                            condicional = true;
                            resultado = false;
                            return resultado;
                        }
                        else{
                            System.out.println("Imposible identificar Accion");
                        }
                    }
            }
                if (vidaJugador <= 0){
                	System.out.println("Haz perdido la lucha contra "+usuario[indiceEnemigo]);
                    condicional = true;
                    resultado = false;
                    }
                else if (vidaEnemigo <= 0){
                	System.out.println("Has ganado la lucha contra "+usuario[indiceEnemigo]);
                    condicional = true;
                    resultado = true;
                }
                else {
                	condicional = false;
                }    
            }
    		System.out.println("--------------------");
            return resultado;
	}
	
	/**
	 * if the user chooses this option, he has to battle against a random enemy
	 * @param sistema - system
	 */
	public static boolean pve(String[] usuario,int [] hp,int [] atk,int [] def,int [] vel,int [] numHechizos,int [] exp,String [] nombreEn,int [] hpEn,int [] atkEn,String [] clase,int [] velEn,int indiceEnemigo,int indiceJugador,String [] hechizos,int [] poder,int cantHechizos,boolean[][] hecJugadores) {
		System.out.println("--------------------");
		Scanner opcion = new Scanner(System.in);
        int accion;
        boolean condicional = false;
        boolean turno;
        boolean resultado = false; //False = derrota, true = Victoria
        int vidaJugador = hp[indiceJugador];
        int vidaEnemigo = hpEn[indiceEnemigo];
        int daño;
        int comprobadorhec;
        int elechech;
        if (vel[indiceJugador] < velEn[indiceEnemigo]){
            turno = false;
        }
        else {
            turno = true;
        }
        while (condicional == false){
            if(turno == false&& vidaEnemigo > 0){
                System.out.println("Enemigo ataco");
                daño = atkEn[indiceEnemigo] - def[indiceJugador];
                if (daño < 0){
                    daño = 0;
                }
                else{
                    vidaJugador = vidaJugador - daño;
                    if (vidaJugador<=0) {
                    	vidaJugador=0;
                    }
                }
                System.out.println(usuario[indiceJugador]+" a recibido "+daño+" puntos de daño"+"\n"+"Su vida restante es "+vidaJugador);
                turno = true;
            }
            else if ((turno == true) && (vidaJugador > 0)){
                while (turno == true){
                    System.out.print("Que desea hacer "+usuario[indiceJugador]+"\n"+"1) Ataque Basico"+"\n"+"2)Lanzar Hechizo"+"\n"+"3)Huir"+"\n"+"---> ");
                    accion = Integer.parseInt(opcion.nextLine());
                    if(accion == 1){
                        System.out.println("Haz realizado un ataque basico");
                        daño = atk[indiceJugador];
                        vidaEnemigo = vidaEnemigo - daño;
                        turno = false;
                        if (vidaEnemigo < 0){
                            vidaEnemigo = 0;
                            System.out.println("Has realizado "+daño+" puntos de daño"+"\n"+"La vida restante de "+nombreEn[indiceEnemigo]+" es "+vidaEnemigo);
                        }
                        else{
                            System.out.println("Has realizado "+daño+" puntos de daño"+"\n"+"La vida restante de "+nombreEn[indiceEnemigo]+" es "+vidaEnemigo);
                        }
                    }
                    else if (accion == 2){
                        comprobadorhec = 0;
                        for(int b = 0; b < cantHechizos;b++){//Comprobador de que tiene hechizos para lanzar
                            if(hecJugadores[b][indiceJugador] == false){
                                comprobadorhec++;
                            }
                        }
                        if(comprobadorhec < cantHechizos){
                            System.out.println("¿Que hechizo desea usar?");
                            int[] hec = new int[numHechizos[indiceJugador]];
                            int coco = 0;
                            for(int jo = 0;jo < cantHechizos;jo++){
                                if (hecJugadores[indiceJugador][jo] == true){
                                    System.out.println(coco+") Hechizo: "+hechizos[jo]);
                                    hec[coco] = jo;
                                    coco++;
                                }
                            }
                            while (turno == true){
                                elechech = Integer.parseInt(opcion.nextLine());
                                if(hecJugadores[indiceJugador][hec[elechech]] == false){
                                    System.out.println("Incomprensible");
                                    turno = true;
                                }
                                else{
                                    System.out.println("Has usado "+hechizos[hec[elechech]]);
                                    daño = poder[hec[elechech]];
                                    vidaEnemigo = vidaEnemigo - daño;
                                    turno = false;
                                    if (vidaEnemigo < 0){
                                        vidaEnemigo = 0;
                                        System.out.println("Has realizado "+daño+" puntos de daño"+"\n"+"La vida restante de "+nombreEn[indiceEnemigo]+" es "+vidaEnemigo);
                                    }
                                    else{
                                        System.out.println("Has realizado "+daño+" puntos de daño"+"\n"+"La vida restante de "+nombreEn[indiceEnemigo]+" es "+vidaEnemigo);
                                    }
                                }
                            }
                        }
                        else{
                            System.out.println("Sin hechizos disponibles");
                            turno = true;
                        }
                    }
                    else if (accion == 3){
                        System.out.println("has corrido por tu vida de "+nombreEn[indiceEnemigo]);
                        condicional = true;
                        resultado = false;
                        return resultado;
                    }
                    else{
                        System.out.println("Imposible identificar Accion");
                    }
                }

            }
            if (vidaJugador == 0){
            	System.out.println("Haz perdido la lucha contra "+nombreEn[indiceEnemigo]);
                condicional = true;
                resultado = false;
                }
            else if (vidaEnemigo == 0){
            	System.out.println("Has ganado la lucha contra "+nombreEn[indiceEnemigo]);
                condicional = true;
                resultado = true;
            }
            else {
            	condicional = false;
            }       
        }
		System.out.println("--------------------");
        return resultado;  
    }
	
	/**
	 * A function used with the pve battle, it chooses the enemy
	 * @param sistema - system
	 */
    public static int eleccionEnemigo(String []clase, int cantEnemigos,String []nombreEn) {
        String eleccion;
        int indice = 0;
        int numero;
        boolean verdadero=true;
        while (verdadero == true){
        	eleccion= repeticion();
            numero = (int)(Math.random()*(cantEnemigos-0)+0);
                if (clase[numero].equals(eleccion)){
                    System.out.println("Enemigo Clase "+eleccion+": "+nombreEn[numero]);
                    indice = numero;
                    verdadero=false;
                    }
                }
        return indice;
    }
    
	/**
	 * A function used with the pvp battle, it chooses the enemy
	 * @param sistema - system
	 */
    public static int eleccionEnemigoPVP(int indice,String[] usuario,int cantJugadores){
        int index = 0;
        int numero;
        boolean verdadero=true;
        while (verdadero == true){
            numero = (int)(Math.random()*(cantJugadores-0)+0);
                if (usuario[numero].equals(usuario[indice])){
                    verdadero=true;
                }
                else{
                    System.out.println("El nombre de tu rival es :"+usuario[numero]);
                    index = numero;
                    verdadero = false;
                }
        }
        return index;
    }
    
	/**
	 * With this, the user can see his own stats and also can look for another player stats
	 * @param sistema - system
	 * @return cae - the class of the enemy
	 */
    public static String repeticion(){
        String cae = "-";
        int cont;
        double f;
        double c;
        double b;
        double a;
        double s;
        boolean jo = false;
        while (jo == false){
            f = Math.random();
            c = Math.random();
            b = Math.random();
            a = Math.random();
            s = Math.random();
            cont = 0;
            if (f < 0.75){
                cae = "F";
                cont++;
            }
            if (c < 0.5){
                cae = "C";
                cont++;
            }
            if (b < 0.25){
                cae = "B";
                cont++;
            }
            if (a < 0.1){
                cae = "A";
                cont++;
            }
            if (s < 0.01){
                cae = "S";
                cont++;
            }
        
            if ((cont > 1) || (cae == "-")){
                
            }
            else{
                jo = true;
            }
        }
        return cae;
    } 
    
	/**
	 * A function used after a pve battle, he can get more stats this way 
	 * @param sistema - system
	 */
    public static void distribucionStats(boolean resultado,int [] hp,int [] atk,int [] def,int [] vel,int indice,int indiceEnemigo,String[] clase){
		System.out.println("--------------------");
    	int puntos;
        Scanner opcion = new Scanner(System.in);
        int eleccion;
        boolean control = false;
        if(resultado == true){
            if(clase[indiceEnemigo].equals("F")){
                puntos = 1;
                System.out.println("Has ganado 1 Punto, ¿a que estadistica se la quieres sumar?");
                for (int i = 0;i < puntos;i++){
                    System.out.print("1) puntos de vida"+"\n"+"2) Ataque"+"\n"+"3) Defensa"+"\n"+"4) Velociodad"+"\n"+"--> ");
                    while(control == false){
                        eleccion = Integer.parseInt(opcion.nextLine());
                        if (eleccion == 1){
                            hp[indice] = hp[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 2){
                            atk[indice] = atk[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 3){
                            def[indice] = def[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 4){
                            vel[indice] = vel[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else{
                            System.out.println("Estadistica invalida");
                            control = false;
                        }
                    }
                }
            }
            else if(clase[indiceEnemigo].equals("C")){
                puntos = 2;
                System.out.println("Has ganado 2 Punto, ¿a que estadistica le quieres ir sumando un punto?");
                for (int i = 0;i < puntos;i++){
                    System.out.print("1) puntos de vida"+"\n"+"2) Ataque"+"\n"+"3) Defensa"+"\n"+"4) Velociodad"+"\n"+"--> ");
                    control=false;
                    while(control == false){
                        eleccion = Integer.parseInt(opcion.nextLine());
                        control=false;
                        if (eleccion == 1){
                            hp[indice] = hp[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 2){
                            atk[indice] = atk[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 3){
                            def[indice] = def[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 4){
                            vel[indice] = vel[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else{
                            System.out.println("Estadistica invalida");
                            control = false;
                        }
                    }
                }
        		System.out.println("--------------------");
            }
            else if(clase[indiceEnemigo].equals("B")){
                puntos = 5;
                System.out.println("Has ganado 5 Punto, ¿a que estadistica le quieres ir sumando un punto?");
                for (int i = 0;i < puntos;i++){
                    System.out.print("1) puntos de vida"+"\n"+"2) Ataque"+"\n"+"3) Defensa"+"\n"+"4) Velociodad"+"\n"+"--> ");
                    control=false;
                    while(control == false){
                        eleccion = Integer.parseInt(opcion.nextLine());
                        control=false;
                        if (eleccion == 1){
                            hp[indice] = hp[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 2){
                            atk[indice] = atk[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 3){
                            def[indice] = def[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 4){
                            vel[indice] = vel[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else{
                            System.out.println("Estadistica invalida");
                            control = false;
                        }
                    }
                }
            }
            else if(clase[indiceEnemigo].equals("A")){
                puntos = 10;
                System.out.println("Has ganado 10 Punto, ¿a que estadistica le quieres ir sumando un punto?");
                for (int i = 0;i < puntos;i++){
                    System.out.print("1) puntos de vida"+"\n"+"2) Ataque"+"\n"+"3) Defensa"+"\n"+"4) Velociodad"+"\n"+"--> ");
                    control=false;
                    while(control == false){
                        eleccion = Integer.parseInt(opcion.nextLine());
                        control=false;
                        if (eleccion == 1){
                            hp[indice] = hp[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 2){
                            atk[indice] = atk[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 3){
                            def[indice] = def[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 4){
                            vel[indice] = vel[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else{
                            System.out.println("Estadistica invalida");
                            control = false;
                        }
                    }
                }
            }
            else if(clase[indiceEnemigo].equals("S")){
                puntos = 20;
                System.out.println("Has ganado 20 Punto, ¿a que estadistica le quieres ir sumando un punto?");
                for (int i = 0;i < puntos;i++){
                    System.out.print("1) puntos de vida"+"\n"+"2) Ataque"+"\n"+"3) Defensa"+"\n"+"4) Velociodad"+"\n"+"--> ");
                    control=false;
                    while(control == false){
                        eleccion = Integer.parseInt(opcion.nextLine());
                        control=false;
                        if (eleccion == 1){
                            hp[indice] = hp[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 2){
                            atk[indice] = atk[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 3){
                            def[indice] = def[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else if (eleccion == 4){
                            vel[indice] = vel[indice] + 1;
                            control = true;
                            System.out.println("Punto añadido");
                        }
                        else{
                            System.out.println("Estadistica invalida");
                            control = false;
                        }
                    }
                }
            }
        }
        else{
            System.out.println("No has ganado puntos");
        }
    }
	
	/**
	 * If the user has enough exp, he can learn a new spell
	 * @param sistema - system
	 */
	public static void aprenderHec(int indiceUser,int cantHechizos,int cantJugadores,int[] numHechizos,int[]exp, String [] hechizos,boolean[][] hecJugadores) {
		System.out.println("--------------------");
		double num= exp[indiceUser]/1000; int numHec= (int)num;
		if ((cantHechizos==numHechizos[indiceUser])&&(numHec==cantHechizos)){
			System.out.println("Numero maximo de hechizos aprendidos, vuelve luego!");
			for (int i=0;cantHechizos-1>=i;i++) {
				System.out.println(hecJugadores[indiceUser][i]);
			}
		}
		else if (numHechizos[indiceUser]>=numHec) {
			System.out.println("no tienes exp suficiente para aprender un hechizo!");
		}
		else {
			int [] hechizosAux= new int[(cantHechizos-numHechizos[indiceUser])+1];
			System.out.println("aprendiendo hechizo..");
			int aux=0;
			for (int i=0;cantJugadores-1>=i;i++) {
				if (i==indiceUser) {
					for (int j=0;cantHechizos-1>=j;j++) {
						if(hecJugadores[i][j]==false) {
							hechizosAux[aux]=j;
							aux++;
						}
					}
				}
			}
			int azar= (int)(Math.random()*((aux)-0)+0);
			int valor=hechizosAux[azar];
			hecJugadores[indiceUser][valor]=true;
			System.out.println("Aprendiste '"+hechizos[valor]+"'!!");
			numHechizos[indiceUser]=numHechizos[indiceUser]+1;			
		}
		System.out.println("--------------------");
	}
	
	/**
	 * With this, the user can see his own stats and also can look for another player stats
	 * @param sistema - system
	 */
	public static void verStats(int cantJugadores,String[]usuario,int[]hp,int[]atk,int[]def,int[]vel,int[]numHechizos) {
		System.out.println("--------------------");
		Scanner leer=new Scanner(System.in);
		System.out.println("ingrese el nombre de algún jugador:");
		String buscar=leer.nextLine();
		boolean find=false;
		for (int i=0;cantJugadores>=i;i++) {
			if (buscar.equals(usuario[i])){
				System.out.println("Se encontró el usuario!");
				System.out.println("Puntos de vida: "+hp[i]);
				System.out.println("Ataque: "+atk[i]);
				System.out.println("defensa: "+def[i]);
				System.out.println("velocidad: "+vel[i]);
				System.out.println("Numero de hechizos: "+numHechizos[i]);
				find=true;
			}
		}
		if (find==false) {
			System.out.println("No se encontró ningún jugador con ese nombre");
		}
		System.out.println("--------------------");
	}
	
	/**
	 * With this, the user can see his own spells and also can look for another player spells
	 * @param sistema - system
	 */
	public static void verStatsHec(int cantJugadores,String[]usuario,int[]numHechizos,String[]hechizos,int cantHechizos,boolean[][]hecJugadores) {
		System.out.println("--------------------");
		Scanner leer=new Scanner(System.in);
		System.out.println("ingrese el nombre de algún jugador:");
		String buscar=leer.nextLine();
		boolean find=false;
		String[]hecAux=new String[cantHechizos];
		for (int i=0;cantJugadores-1>=i;i++) {
			if (buscar.equals(usuario[i])){
				System.out.println("Se encontró el usuario!");
				System.out.println("Numero de hechizos: "+numHechizos[i]);
				for (int j=0;cantHechizos-1>=j;j++) {
					if (hecJugadores[i][j]==true) {
						System.out.println(hechizos[j]);
					}
				}
				find=true;
			}
		}
		if (find==false) {
			System.out.println("No se encontró ningún jugador con ese nombre");
		}
		System.out.println("--------------------");
	}
	
	/**
	 * This function shows the current rankings based on exp 
	 * @param sistema - system
	 */
	public static void ranking(int cantJugadores,String[]usuario,int[]exp) {
		System.out.println("--------------------");
		String[]ranking= new String[cantJugadores];
		for (int i=0;cantJugadores-1>=i;i++) {
			ranking[i]=usuario[i];
		}
		int[]auxExp=new int[cantJugadores];
		for (int j=0;cantJugadores-1>=j;j++) {
			auxExp[j]=exp[j];
		}
		ordenburbuja(auxExp,ranking);
		int contador=1;
		System.out.println("El ranking actual es:");
		for (int z=0;cantJugadores-1>=z;z++) {
			System.out.println(contador+") "+ranking[z]+" con un total de: "+auxExp[z]);
			contador++;
			if (contador==11) {
				break;
			}
		}
		System.out.println("--------------------");
	}
	
	/**
	 * This function orders a vector from the highest value to the lowest 
	 * @param sistema - system
	 */
    public static void ordenburbuja(int[] lista1,String[]lista2){
      int aux;String aux2;
      for(int i = 2; i <= lista1.length; i++){
        for(int j = 0;j <= lista1.length-i;j++){
          if(lista1[j] < lista1[j+1]){
            aux = lista1[j];
            lista1[j] = lista1[j+1];
            lista1[j+1] = aux;
            aux2= lista2[j];
            lista2[j]=lista2[j+1];
            lista2[j+1]=aux2;
          }   
        }
      }
    }
 
	//ADMIN
	
	/**
	 * with this, the admin can delete a player from the sistem
	 * @param sistema - system
	 * @return cantJugadores - the new number of current players
	 */
    public static int eliminarJugador(int cantJugadores,int cantHechizos,String[] usuario,String [] contraseña,int [] hp,int [] atk,int [] def,int [] vel,int [] numHechizos,int [] exp,boolean [][]hecJugadores) {
    	Scanner leer= new Scanner(System.in);
    	if (cantJugadores==0) {
    		System.out.println("No existe ningún jugador");
    	}
    	else {
    		System.out.println("Ingrese el numero del usuario que desea eliminar:");
        	for (int j=0;cantJugadores-1>=j;j++) {
        		System.out.println(j+1+")"+usuario[j]);
        	}
    		int eliminarPosicion=Integer.parseInt(leer.nextLine());
    		while(eliminarPosicion-1>=cantJugadores) {
    			System.out.println("Numero no valido, intente nuevamente");
    			System.out.println("Ingrese el numero del usuario que desea eliminar:");
    	    	for (int j=0;cantJugadores-1>=j;j++) {
    	    		System.out.println(j+1+")"+usuario[j]);
    	    	}
    			eliminarPosicion=Integer.parseInt(leer.nextLine());
    		}
    		for (int i = eliminarPosicion-1; i < cantJugadores; i++) {
    			(usuario[i]) = (usuario[i+1]);
    			(contraseña[i])= (contraseña[i+1]);
    			hp[i]= (hp[i+1]);
    			(atk[i])= (atk[i+1]);
    			def[i]= (def[i+1]);
    			vel[i]= (vel[i+1]);
    			numHechizos[i]= (numHechizos[i+1]);
    			exp[i]= (exp[i+1]);
    			for (int j=0;cantHechizos>=j;j++) {
    				hecJugadores[i][j]=hecJugadores[i+1][j];
    			}
    		}
    	}
    	return cantJugadores+=-1;
    	}
	
	/**
	 * the admin can create a new enemy
	 * @param sistema - system
	 * @return cantEnemigos - the new number of current enemies
	 */
	public static int agregarEn(String[]nombreEn,int [] hpEn,int [] atkEn,String [] clase,int [] velEn,int cantEnemigos) {
		System.out.println("--------------------");
		Scanner leer= new Scanner(System.in);
		System.out.println("¿Desea agregar un enemigo nuevo al juego? (ingrese el numero)");
		System.out.println("1)Si");System.out.println("2)No");
		String respuesta= leer.nextLine(); int repeticiones=0;
		String nombre; String nombreAux;String nombreAux2;
		boolean error=false;
		while (!respuesta.equals("2")) {
			error=false;
			if (respuesta.equals("1")) {
				System.out.println("ingrese nombre del nuevo enemigo:");
				nombre= leer.nextLine();
				for (int i=0;i<=cantEnemigos-1;i++) {
					nombreAux=nombre.toUpperCase();
					nombreAux2= nombreEn[i].toUpperCase();
					if (nombreAux.equals(nombreAux2)){
						System.out.println("Enemigo ya existente, intente de nuevo");
						error=true;
					}
					else {
						nombreEn[cantEnemigos]=nombre;
					}
				}
				if(error==false) {
					System.out.println("ingrese los puntos de vida que tendrá (solo numeros)");
					String ahp= leer.nextLine();int hp=Integer.parseInt(ahp);
					hpEn[cantEnemigos]=hp;
					System.out.println("ingrese el ataque que tendrá (solo numeros)");
					String aatk= leer.nextLine();int atk=Integer.parseInt(aatk);
					atkEn[cantEnemigos]=atk;
					System.out.println("ingrese la velocidad que tendrá (solo numeros)");
					String avel= leer.nextLine();int vel=Integer.parseInt(avel);
					velEn[cantEnemigos]=vel;
					System.out.println("ingrese la clase que tendrá (SOLO F,D,C,B,A,S)");
					String clas= leer.nextLine().toUpperCase();
					while (!clas.equals("S")&&!clas.equals("A")&&!clas.equals("B")&&!clas.equals("C")&&!clas.equals("D")&&!clas.equals("F")) {
						System.out.println("intente nuevamente");
						System.out.println("ingrese la clase que tendrá (SOLO F,D,C,B,A,S)");
						clas= leer.nextLine().toUpperCase();
					}
					clase[cantEnemigos]=clas;
					cantEnemigos++;
					repeticiones++;
					System.out.println("Enemigo agregado!!");
					System.out.println("¿Desea agregar un enemigo nuevo al juego? (ingrese el numero)");
					System.out.println("1)Si");System.out.println("2)No");
					respuesta= leer.nextLine();
				}
			}
		
			else {
				System.out.println("opción no valida, responda nuevamente");
				System.out.println("¿Desea agregar un enemigo nuevo al juego? (ingrese el numero)");
				System.out.println("1)Si");System.out.println("2)No");
				respuesta= leer.nextLine();
			}
		}
		for (int i=0;cantEnemigos-1>=i;i++) {
			System.out.println(nombreEn[i]);
		}
		
		System.out.println("--------------------");
		return repeticiones;
	}
	
	/**
	 * the admin can create a new spell
	 * @param sistema - system
	 * @return cantHechizos - the new number of current spells
	 */
	public static int agregarHec(String[] hechizos,int[]poder,int cantHechizos) {
		System.out.println("--------------------");
		Scanner leer= new Scanner(System.in);
		System.out.println("¿Desea agregar un hechizo nuevo al juego? (ingrese el numero)");
		System.out.println("1)Si");System.out.println("2)No");
		String respuesta= leer.nextLine(); int repeticiones=0;
		String nombre; String nombreAux;String nombreAux2;
		boolean error=false;
		while (!respuesta.equals("2")) {
			error=false;
			if (respuesta.equals("1")) {
				System.out.println("ingrese nombre del nuevo hechizo:");
				nombre= leer.nextLine();
				for(int i=0;i<=cantHechizos-1;i++) {
					nombreAux=nombre.toUpperCase();
					System.out.println(hechizos[i]);
					nombreAux2= hechizos[i].toUpperCase();
					if (nombreAux.equals(nombreAux2)){
						System.out.println("Hechizo ya existente, intente de nuevo");
						error=true;
					}
					else {
						hechizos[cantHechizos]=nombre;
					}
				}
				if(error==false) {
					System.out.println("ingrese el poder que tendrá (solo numeros)");
					String ap= leer.nextLine();int apow=Integer.parseInt(ap);
					poder[cantHechizos]=apow;
					System.out.println("hechizo agregado!!");
					cantHechizos++;
					repeticiones++;
					System.out.println("¿Desea agregar un hechizo nuevo al juego? (ingrese el numero)");
					System.out.println("1)Si");System.out.println("2)No");
					respuesta= leer.nextLine();
				}
			}
			else {
				System.out.println("opción no valida, responda nuevamente");
				System.out.println("¿Desea agregar un hechizo nuevo al juego? (ingrese el numero)");
				System.out.println("1)Si");System.out.println("2)No");
				respuesta= leer.nextLine();
			}
		}
		System.out.println("--------------------");
		return repeticiones;
	}
	
	/**
	 * With this, the admin can look for all players stats
	 * @param sistema - system
	 */
	public static void verStatsAdmin(int cantJugadores,String[]usuario,int[]hp,int[]atk,int[]def,int[]vel,int[]numHechizos,int[]exp) {
		System.out.println("--------------------");
		for (int i=0;cantJugadores-1>=i;i++) {
			System.out.println(usuario[i]);
			System.out.print(" Hp: "+hp[i]);
			System.out.print(" Atk: "+atk[i]);
			System.out.print(" Def: "+def[i]);
			System.out.print(" Vel: "+vel[i]);
			System.out.print(" N°Hechizos: "+numHechizos[i]);
			System.out.println(" Exp: "+exp[i]);System.out.println("");
			}
		System.out.println("--------------------");
	}
	
	//CLOSE
	
	/**
	 * A function that overwrites the file "Jugadores.txt" when the system is closed
	 * @param sistema - system
	 * @throws IOException - An exception
	 */
	public static void cerrarJugadores(int cantJugadores,String []usuario,String[]contraseña,int[]hp,int[]atk,int[]def,int[]vel,int[]numHechizos,int[]exp) throws IOException{ 
        FileWriter arch = new FileWriter("Jugadores.txt"); 
        BufferedWriter agregar = new BufferedWriter(arch);
        String linea;
        for (int i=0;cantJugadores-1>=i;i++) {
        	linea=usuario[i]+","+contraseña[i]+","+hp[i]+","+atk[i]+","+def[i]+","+vel[i]+","+numHechizos[i]+","+exp[i];
        	agregar.write(linea+"\n");
        }
        agregar.close();
	}
	
	/**
	 * A function that overwrites the file "Hechizos.txt" when the system is closed
	 * @param sistema - system
	 * @throws IOException - An exception
	 */
	public static void cerrarHechizos(int cantHechizos,String[]hechizos,int[]poder)throws IOException {
        FileWriter arch = new FileWriter("Hechizos.txt"); 
        BufferedWriter agregar = new BufferedWriter(arch);
        String linea;
        for (int i=0;cantHechizos-1>=i;i++) {
        	linea=hechizos[i]+","+poder[i];
        	agregar.write(linea+"\n");
        	
        }
        agregar.close();
	}
	
	/**
	 * A function that overwrites the file "HechizosJugadores.txt" when the system is closed
	 * @param sistema - system
	 * @throws IOException - An exception
	 */
	public static void cerrarHechizosJugadores(int cantJugadores,int cantHechizos,String[]usuario,String[]hechizo,boolean[][]hecJugadores)throws IOException {
        FileWriter arch = new FileWriter("HechizosJugadores.txt"); 
        BufferedWriter agregar = new BufferedWriter(arch);
        String linea;
        for (int i=0;cantJugadores-1>=i;i++) {
        	for(int j=0;cantHechizos-1>=j;j++) {
        		if (hecJugadores[i][j]==true) {
        			linea=usuario[i]+","+hechizo[j];
        			agregar.write(linea+"\n");
        		}
        	}
        }
        agregar.close();
	}
	
	/**
	 * A function that overwrites the file "Enemigos.txt" when the system is closed
	 * @param sistema - system
	 * @throws IOException - An exception
	 */
	public static void cerrarEnemigos(int cantEnemigos,String[]nombreEn,int[]hpEn,int[]atkEn,String[]clase,int[]velEn)throws IOException {
        FileWriter arch = new FileWriter("Enemigos.txt"); 
        BufferedWriter agregar = new BufferedWriter(arch);
        String linea;
        for (int i=0;cantEnemigos-1>=i;i++) {
        	linea=nombreEn[i]+","+hpEn[i]+","+atkEn[i]+","+clase[i]+","+velEn[i];
        	agregar.write(linea+"\n");
        }
        agregar.close();
	}
		
}