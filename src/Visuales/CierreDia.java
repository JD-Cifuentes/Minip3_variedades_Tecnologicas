package Visuales;

import Entidades.Negocio;
import Entidades.Operador;
import Entidades.ServicioImpresora;
import Utils.AccessPanel;
import Utils.ManejoTintas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CierreDia implements AccessPanel {
    private JPanel CierreDia_BG;
    private JButton bttn_generarCierreDia;
    private JLabel lbl_totalDineroRecogido;
    private JLabel lbl_impresoraConMasConsumo;
    private JLabel lbl_totalGanacia;
    private JLabel lbl_gastosDeProduccion;
    private JLabel lbl_servicioConMayorGanacia;
    private JLabel lbl_impresoraConMenorGanacia;
    private JTextField txtReadOnly_totalDineroRecogido;
    private JTextField txtReadOnly_gananciaObtenida;
    private JTextField txtReadOnly_gastosProduccion;
    private JTextField txtReadOnly_servicioConMayorGanancia;
    private JTextField txtReadOnly_impresoraConmenorGanacia;
    private JTextField txtReadOnly_impresoraMayorConsumoTinta;

    public CierreDia(Negocio local) {

        bttn_generarCierreDia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Utilizamos en este punto la clase Object para poder capturar el objeto que tenga mayor ganancia,
                //esto gracias a que Object es la clase base para todas las clases que se crean en un proyecto, ya que
                //está implicitamente heredada en cada una y por lo tanto, al referenciar la variable servicioConMayorGanacias
                //con Object, se nos permite alojar la referencia a cualquier tipo de objeto ya que todas las clases derivan de esta.
                Object servicioConMayorGanacias = null;
                ServicioImpresora servConMenorGanacia = null;
                double menorGanaciaDeImpresora = 0.0;
                double ganaciaMax = 0.0;
                double totalIngresos = 0.0;
                double totalCostos = 0.0;
                double ganacias = 0.0;

                for (ServicioImpresora servimp : local.getServicioImpresora()) {
                    totalCostos += servimp.valorCostos();
                    totalIngresos += servimp.getTotalIngresos();
                    ganacias += servimp.ganancia();

                    if(servimp.ganancia() > ganaciaMax){
                        ganaciaMax = servimp.ganancia();
                        servicioConMayorGanacias = servimp;
                    }

                    if(menorGanaciaDeImpresora == 0.0 || menorGanaciaDeImpresora > servimp.ganancia()) {
                        menorGanaciaDeImpresora = servimp.ganancia();
                        servConMenorGanacia = servimp;
                    }
                }
                if(servConMenorGanacia == servicioConMayorGanacias || servicioConMayorGanacias == null){
                    servConMenorGanacia = null;
                    menorGanaciaDeImpresora = 0.0;
                }

                for (Operador serOperador : local.getOperadores()) {
                    totalCostos += serOperador.valorCostos();
                    totalIngresos += serOperador.getTotalIngreso();
                    ganacias += serOperador.ganancia();
                    if (serOperador.ganancia() > ganaciaMax) {
                        ganaciaMax = serOperador.ganancia();
                        servicioConMayorGanacias = serOperador;
                    }
                }

                String[][] totalesConsumoDeTintas = ManejoTintas.totalesConsumoTintas();
                String impresoraConMayorConsumoTinta = "";
                double consumoTintaMayor = 0.0;
                for(String[] totalTinta : totalesConsumoDeTintas){
                    if(Double.parseDouble(totalTinta[1]) > consumoTintaMayor){
                        consumoTintaMayor = Double.parseDouble(totalTinta[1]);
                        impresoraConMayorConsumoTinta = totalTinta[0];
                    }
                }

                totalCostos += local.getCostoEmpleadoDia() + local.getCostoEnergiaDia();

                txtReadOnly_totalDineroRecogido.setText("$" + totalIngresos);
                txtReadOnly_gananciaObtenida.setText("$" + ganacias);
                txtReadOnly_gastosProduccion.setText("$" + totalCostos);
                txtReadOnly_servicioConMayorGanancia.setText(
                        ((servicioConMayorGanacias == null) ? "Ningún servicio facturado"
                                : servicioConMayorGanacias + " con $" + ganaciaMax));
                txtReadOnly_impresoraConmenorGanacia.setText(
                        ((servConMenorGanacia == null) ? "Falta facturar impresoras"
                                : servConMenorGanacia + " con $" + menorGanaciaDeImpresora));
                txtReadOnly_impresoraMayorConsumoTinta.setText(
                        ((consumoTintaMayor > 0.0)
                                ? impresoraConMayorConsumoTinta + " %" + consumoTintaMayor
                                : "No hay consumo de tinta"));

                //toString es uno de los métodos declarados en la clase Object, por ello cualquier objeto de cualquier clase tiene acceso
                //a este, y gracias al polimorfismo en tiempo de ejecución, el resultado retornado será el declarado dentro de la clase
                //a la cual pertenece el objeto del que se ha guardado la referencia en la variable servicioConMayorGanacias.

            }
        });

        CierreDia_BG.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                txtReadOnly_totalDineroRecogido.setText("");
                txtReadOnly_gananciaObtenida.setText("");
                txtReadOnly_gastosProduccion.setText("");
                txtReadOnly_servicioConMayorGanancia.setText("");
                txtReadOnly_impresoraConmenorGanacia.setText("");
                txtReadOnly_impresoraMayorConsumoTinta.setText("");
            }
        });



    }



    @Override
    public JPanel getPanel() {
        return CierreDia_BG;
    }
}
