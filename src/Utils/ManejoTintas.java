package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class ManejoTintas {
	private static final DecimalFormatSymbols simbolos = new DecimalFormatSymbols(Locale.US){{
		setDecimalSeparator('.');
	}};
	private static final DecimalFormat df = new DecimalFormat("#.00", simbolos);
	private static final String rutaTintaTxt = "src/BD/estado_tintas.txt";
	private static final int factorReduccionParaPlotter = 10000;
	private static final int factorAumentoConsumoTintaNegra = 5;
	private static final int cantidadFilasTxt = 21;
	private static final int cantidadColumnasTxt = 3;
	private static final int cantidadCartuchos = 4;


	public enum tipoImpresoraEnBDTxt{
		FOTOCOPIA,
		LASER,
		PLOTTER
	}

	public static String[][] totalesConsumoTintas() {
		String[][] totales = new String[tipoImpresoraEnBDTxt.values().length][2];
		String[][] estadoCartuchos = new String[cantidadFilasTxt][cantidadColumnasTxt];

		try (BufferedReader lineas = new BufferedReader(new FileReader(rutaTintaTxt))) {
			String linea;
			int i = 0;
			while( (linea=lineas.readLine()) != null) {
				String[] datoTinta = linea.split(";");
				estadoCartuchos[i] = datoTinta;
				i++;
			}
		}catch (IOException e) {
			System.out.println("Error! :"+e);
		}
		int indexParaTotales = 0;
		for(tipoImpresoraEnBDTxt tipo : tipoImpresoraEnBDTxt.values()) {
			for(int fila = 0; fila < cantidadFilasTxt; fila++) {
				if(estadoCartuchos[fila][0].equals(tipo.toString().toLowerCase())) {
					totales[indexParaTotales][0] = tipo.toString().toLowerCase();
					totales[indexParaTotales][1] = estadoCartuchos[fila+5][1];
					indexParaTotales++;
					break;
				}
			}
		}


		return totales;
	}

	public static String[][] verTinta(tipoImpresoraEnBDTxt tImp) {
		String[][] estadoCartuchos = new String[cantidadCartuchos][cantidadColumnasTxt-1];
        try (BufferedReader lineas = new BufferedReader(new FileReader(rutaTintaTxt))) {

			String linea;
			while( (linea=lineas.readLine()) != null) {
				if(linea.contains(tImp.toString().toLowerCase())){
					for(int i = 0; i < 4; i++) {
						linea = lineas.readLine();
						String[] datoTinta = linea.split(";");
						estadoCartuchos[i][0] = datoTinta[0];
						estadoCartuchos[i][1] = datoTinta[1];
					}
					break;
				}
			}
		}catch (IOException e) {
			System.out.println("Error! :"+e);
		}

		return estadoCartuchos;
    }

	public static boolean validarNivelTinta(tipoImpresoraEnBDTxt tImp, double cantidadInsumoAVender){
		String[][] estadoCartuchos = new String[cantidadFilasTxt][cantidadColumnasTxt];
		try (BufferedReader lineas = new BufferedReader(new FileReader(rutaTintaTxt))) {
			String linea;
			int cnt = 0;

			while( (linea=lineas.readLine()) != null) {
				String[] datoTinta = linea.split(";");
				estadoCartuchos[cnt] = datoTinta;
				cnt++;
			}

			for(int i = 0; i < estadoCartuchos.length; i++){
				if(estadoCartuchos[i][0].contains(tImp.toString().toLowerCase())){
					int finalRangoDeTintas = i + cantidadCartuchos;
					while(finalRangoDeTintas > i){
						i++;
						double porcentajeTinta = Double.parseDouble(estadoCartuchos[i][1]);
						double pasoDisminucionTinta = Double.parseDouble(estadoCartuchos[i][2]);

						if (porcentajeTinta < pasoDisminucionTinta*cantidadInsumoAVender/ factorReduccionParaPlotter) {
							return false;
						}
					}
					break;
				}
			}

			return true;

		}catch (IOException e) {
			System.out.println("Error! :"+e);
			return false;
		}
	}

	public static boolean validarNivelTinta(tipoImpresoraEnBDTxt tImp, int cantidadInsumoAVender, boolean esColor){
		String[][] estadoCartuchos = new String[cantidadFilasTxt][cantidadColumnasTxt];
		try (BufferedReader lineas = new BufferedReader(new FileReader(rutaTintaTxt))) {
			String linea;
			int cnt = 0;

			while( (linea=lineas.readLine()) != null) {
				String[] datoTinta = linea.split(";");
				estadoCartuchos[cnt] = datoTinta;
				cnt++;
			}

			for(int i = 0; i < estadoCartuchos.length; i++){
				if(estadoCartuchos[i][0].contains(tImp.toString().toLowerCase())){
					int finalRangoDeTintas = i + cantidadCartuchos;
					if(esColor){
						while(finalRangoDeTintas > i){
							i++;
							double porcentajeTinta = Double.parseDouble(estadoCartuchos[i][1]);
							double pasoDisminucionTinta = Double.parseDouble(estadoCartuchos[i][2]);

							if (porcentajeTinta < pasoDisminucionTinta*cantidadInsumoAVender) {
								return false;
							}
						}
					}else{
						double porcentajeTinta = Double.parseDouble(estadoCartuchos[finalRangoDeTintas][1]);
						double pasoDisminucionTinta = Double.parseDouble(estadoCartuchos[finalRangoDeTintas][2]);

						if (porcentajeTinta < pasoDisminucionTinta*cantidadInsumoAVender*factorAumentoConsumoTintaNegra) {
							return false;
						}
					}
					break;
				}
			}
			return true;
		}catch (IOException e) {
			System.out.println("Error! :"+e);
			return false;
		}
	}

    public static void mermarNivelTinta(tipoImpresoraEnBDTxt tImp, double cantidadInsumoAVender) {

		String[][] estadoCartuchos = new String[cantidadFilasTxt][cantidadColumnasTxt];
		
		try (BufferedReader lineas = new BufferedReader(new FileReader(rutaTintaTxt))) {
			String linea;
			int cnt = 0;

			while( (linea=lineas.readLine()) != null) {
				String[] datoTinta = linea.split(";");
				estadoCartuchos[cnt] = datoTinta;
				cnt++;
			}

			for(int i = 0; i < estadoCartuchos.length; i++){
				if(estadoCartuchos[i][0].contains(tImp.toString().toLowerCase())){
					int finalRangoDeTintas = i + cantidadCartuchos;
					int indiceTotalConsumoDeTinta = finalRangoDeTintas + 1;
					double porcentajeTotalTintaConsumida = Double.parseDouble(estadoCartuchos[indiceTotalConsumoDeTinta][1]);
					while(finalRangoDeTintas > i){
						i++;
						double porcentajeTinta = Double.parseDouble(estadoCartuchos[i][1]);
						double pasoDisminucionTinta = Double.parseDouble(estadoCartuchos[i][2]);
						double tintaConsumida = pasoDisminucionTinta*cantidadInsumoAVender/ factorReduccionParaPlotter;

						if (porcentajeTinta > tintaConsumida) {
							porcentajeTinta = porcentajeTinta - tintaConsumida;
						}else{
							throw new IllegalArgumentException("Tinta insuficiente: " + estadoCartuchos[i][0]);
						}
						estadoCartuchos[i][1] = df.format(porcentajeTinta);
						estadoCartuchos[indiceTotalConsumoDeTinta][1] =
								df.format(tintaConsumida + porcentajeTotalTintaConsumida);

					}
					break;
				}
			}

		}catch (IOException e) {
			System.out.println("Error! :"+e);
		}
		escribirNivelTinta(estadoCartuchos);
    }

	public static void mermarNivelTinta(tipoImpresoraEnBDTxt tImp, int cantidadInsumoAVender, boolean esColor) {

		String[][] estadoCartuchos = new String[cantidadFilasTxt][cantidadColumnasTxt];

		try (BufferedReader lineas = new BufferedReader(new FileReader(rutaTintaTxt))) {
			String linea;
			int cnt = 0;

			while( (linea=lineas.readLine()) != null) {
				String[] datoTinta = linea.split(";");
				estadoCartuchos[cnt] = datoTinta;
				cnt++;
			}

			for(int i = 0; i < estadoCartuchos.length; i++){
				if(estadoCartuchos[i][0].contains(tImp.toString().toLowerCase())){
					int finalRangoDeTintas = i + cantidadCartuchos;
					int indiceTotalConsumoDeTinta = finalRangoDeTintas + 1;
					double porcentajeTotalTintaConsumida = Double.parseDouble(estadoCartuchos[indiceTotalConsumoDeTinta][1]);

					if(esColor){
						while(finalRangoDeTintas > i){
							i++;
							double porcentajeTinta = Double.parseDouble(estadoCartuchos[i][1]);
							double pasoDisminucionTinta = Double.parseDouble(estadoCartuchos[i][2]);
							double tintaConsumida = pasoDisminucionTinta*cantidadInsumoAVender;

							if (porcentajeTinta > tintaConsumida) {
								porcentajeTinta = porcentajeTinta - tintaConsumida;
							}else{
								throw new IllegalArgumentException("Tinta insuficiente: " + estadoCartuchos[i][0]);
							}
							estadoCartuchos[i][1] = df.format(porcentajeTinta);
							estadoCartuchos[indiceTotalConsumoDeTinta][1] =
									df.format(tintaConsumida + porcentajeTotalTintaConsumida);
						}
					}else {
						double porcentajeTinta = Double.parseDouble(estadoCartuchos[finalRangoDeTintas][1]);
						double pasoDisminucionTinta = Double.parseDouble(estadoCartuchos[finalRangoDeTintas][2]);
						double tintaConsumida = pasoDisminucionTinta*cantidadInsumoAVender*factorAumentoConsumoTintaNegra;

						if (porcentajeTinta > tintaConsumida) {
							porcentajeTinta = porcentajeTinta - tintaConsumida;
						}else{
							throw new IllegalArgumentException("Tinta insuficiente: " + estadoCartuchos[finalRangoDeTintas][0]);
						}
						estadoCartuchos[finalRangoDeTintas][1] = df.format(porcentajeTinta);
						estadoCartuchos[indiceTotalConsumoDeTinta][1] =
								df.format(tintaConsumida + porcentajeTotalTintaConsumida);
					}

					break;
				}
			}

		}catch (IOException e) {
			System.out.println("Error! :"+e);
		}
		escribirNivelTinta(estadoCartuchos);
	}

	private static void escribirNivelTinta(String[][] estadoCartuchos){

		try (FileWriter writer = new FileWriter(rutaTintaTxt, false)) {

			for(int i = 0; i < estadoCartuchos.length; i++){
				if(estadoCartuchos[i][0] == null){
					writer.write("");
					continue;
				}else if(estadoCartuchos[i].length == 1) {
					writer.write(estadoCartuchos[i][0]);
				}else if(estadoCartuchos[i].length == 2){
					writer.write(estadoCartuchos[i][0]
							+ ";" + estadoCartuchos[i][1]);
				}else{
					writer.write(estadoCartuchos[i][0]
							+ ";" + estadoCartuchos[i][1]
							+ ";" + estadoCartuchos[i][2]);
				}
				writer.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}