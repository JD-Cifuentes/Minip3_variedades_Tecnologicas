package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class ManejoTintas {

	private static String rutaTintaTxt = "src/BD/estado_tintas.txt";

	public enum tipoImpresoraEnBDTxt{
		TINTA,
		LASER,
		PLOTTER
	}

	public static String[][] verTinta(tipoImpresoraEnBDTxt tImp) {
		String[][] estadoCartuchos = new String[4][2];
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

	public static boolean validarNivelTinta(tipoImpresoraEnBDTxt tImp){
		String[][] estadoCartuchos = new String[18][3];
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
					int indicetitulo = i + 4;
					while(indicetitulo > i){
						i++;
						double porcentajeTinta = Double.parseDouble(estadoCartuchos[i][1]);
						double pasoDisminucionTinta = Double.parseDouble(estadoCartuchos[i][2]);

						if (porcentajeTinta < pasoDisminucionTinta) {
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

    public static void mermarNivelTinta(tipoImpresoraEnBDTxt tImp) {
		
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols(Locale.US);
        simbolos.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", simbolos);
		String[][] estadoCartuchos = new String[18][3];
		
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
					int indicetitulo = i + 4;
					while(indicetitulo > i){
						i++;
						double porcentajeTinta = Double.parseDouble(estadoCartuchos[i][1]);
						double pasoDisminucionTinta = Double.parseDouble(estadoCartuchos[i][2]);
						
						 if (porcentajeTinta > pasoDisminucionTinta) {
							 porcentajeTinta = porcentajeTinta - pasoDisminucionTinta;
						 }else{
							 throw new IllegalArgumentException("Tinta insuficiente: " + estadoCartuchos[i][0]);
						 }
						estadoCartuchos[i][1] = df.format(porcentajeTinta);
					}
					break;
				}
			}

		}catch (IOException e) {
			System.out.println("Error! :"+e);
			return;
		}
		
		try (FileWriter writer = new FileWriter(rutaTintaTxt, false)) {
            
			for(int i = 0; i < estadoCartuchos.length; i++){
				if(estadoCartuchos[i][0] == null){
					writer.write("");
					continue;
				}else if(estadoCartuchos[i].length == 1){
					writer.write(estadoCartuchos[i][0]);
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