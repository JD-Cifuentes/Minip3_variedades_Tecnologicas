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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    private JLabel lbl_tituloCierreDia;

    public CierreDia(Negocio local) {

        bttn_generarCierreDia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String servicioConMayorGanacias = "";
                double ganaciaMax = 0.0;
                String servImprConMenorGanacia = "";
                double menorGanaciaDeImpresora = 0.0;

                double totalIngresos = 0.0;
                double totalCostos = 0.0;
                double ganacias = 0.0;

                String[][] totalesConsumoDeTintas = ManejoTintas.totalesConsumoTintas();
                String impresoraConMayorConsumoTinta = "";
                double consumoTintaMayor = 0.0;

                HashMap<String, Double> ganaciasImpresorasRegistradas = new HashMap<>();
                HashMap<String, Double> ganaciasOperadoresRegistrados = new HashMap<>();

                for(String[] impresorasRegistradas : totalesConsumoDeTintas){
                    if(!ganaciasImpresorasRegistradas.containsKey(impresorasRegistradas[0])){
                        ganaciasImpresorasRegistradas.put(impresorasRegistradas[0], 0.0);
                    }
                }

                for(String[] totalTinta : totalesConsumoDeTintas){
                    if(Double.parseDouble(totalTinta[1]) > consumoTintaMayor){
                        consumoTintaMayor = Double.parseDouble(totalTinta[1]);
                        impresoraConMayorConsumoTinta = totalTinta[0];
                    }
                }

                for (ServicioImpresora servimp : local.getServicioImpresora()) {
                    totalCostos += servimp.valorCostos();
                    totalIngresos += servimp.getTotalIngresos();
                    ganacias += servimp.ganancia();

                    for(String[] imprReg : totalesConsumoDeTintas){
                        if(servimp.getTipo().toLowerCase().contains(imprReg[0])){
                            String impresora = imprReg[0];
                            double gananciaActualDeLaImpr = ganaciasImpresorasRegistradas.get(impresora);
                            ganaciasImpresorasRegistradas.replace(impresora, gananciaActualDeLaImpr+servimp.ganancia());
                        }
                    }
                }

                ArrayList<Operador> listaOperadores = local.getOperadores();
                HashSet<Operador> setOperadores = new HashSet<>(listaOperadores);

                for(Operador operador : setOperadores) {
                    ganaciasOperadoresRegistrados.put(operador.getNombre(), 0.0);
                }


                for (Operador serOperador : local.getOperadores()) {
                    totalCostos += serOperador.valorCostos();
                    totalIngresos += serOperador.getTotalIngreso();
                    ganacias += serOperador.ganancia();

                    for(Operador op: setOperadores){
                        if(op.getNombre().equals(serOperador.getNombre())){
                            String operador = op.getNombre();
                            double gananciaActualDelOp = ganaciasOperadoresRegistrados.get(operador);
                            ganaciasOperadoresRegistrados.replace(operador, gananciaActualDelOp + serOperador.ganancia());
                        }
                    }
                }
                System.out.println(ganaciasImpresorasRegistradas);


                for(String imp : ganaciasImpresorasRegistradas.keySet()){
                    double gananciaLeida = ganaciasImpresorasRegistradas.get(imp);
                    if((ganaciaMax == .0 && gananciaLeida != .0) || ganaciaMax < gananciaLeida){
                        ganaciaMax = gananciaLeida;
                        servicioConMayorGanacias = imp;
                    }

                    if((menorGanaciaDeImpresora == .0 && gananciaLeida != .0) || gananciaLeida < menorGanaciaDeImpresora){
                        menorGanaciaDeImpresora = gananciaLeida;
                        servImprConMenorGanacia = imp;
                    }

                }

                for(String op : ganaciasOperadoresRegistrados.keySet()){
                    double gananciaLeida = ganaciasOperadoresRegistrados.get(op);
                    if((ganaciaMax == .0 && gananciaLeida != .0) || ganaciaMax < gananciaLeida){
                        ganaciaMax = gananciaLeida;
                        servicioConMayorGanacias = op;
                    }
                }



                totalCostos += local.getCostoEmpleadoDia() + local.getCostoEnergiaDia();

                txtReadOnly_totalDineroRecogido.setText("$" + totalIngresos);
                txtReadOnly_gananciaObtenida.setText("$" + ganacias);
                txtReadOnly_gastosProduccion.setText("$" + totalCostos);
                txtReadOnly_servicioConMayorGanancia.setText(
                        ((servicioConMayorGanacias.isBlank()) ? "Ningún servicio facturado"
                                : servicioConMayorGanacias + " con $" + ganaciaMax));
                txtReadOnly_impresoraConmenorGanacia.setText(
                        ((servImprConMenorGanacia.isBlank()) ? "Falta facturar impresoras"
                                : servImprConMenorGanacia + " con $" + menorGanaciaDeImpresora));
                txtReadOnly_impresoraMayorConsumoTinta.setText(
                        ((consumoTintaMayor > 0.0)
                                ? impresoraConMayorConsumoTinta + " %" + consumoTintaMayor
                                : "No hay consumo de tinta"));
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
