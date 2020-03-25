/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto.listas;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author jediazs
 */
public class TipoCuentaMultifuncionalLista {

    public List<SelectItem> lista;

    public List<SelectItem> crearLista() {
        lista = new ArrayList<>();
        SelectItem item = new SelectItem();

        item.setValue("Cuenta Ahorros");
        item.setLabel("Cuenta Ahorros");
        lista.add(item);
        item = new SelectItem();

        item.setValue("Cuenta Corriente");
        item.setLabel("Cuenta Corriente");
        lista.add(item);
        item = new SelectItem();
        item.setValue("Credito FM");
        item.setLabel("Credito FM");
        lista.add(item);
        item = new SelectItem();
        item.setValue("Tarjeta Credito");
        item.setLabel("Tarjeta Credito");
        lista.add(item);

        return lista;
    }

    public String getLabelItem(String value) {
        String resp = "";
        for (SelectItem item : lista) {
            if (item.getValue().equals(value)) {
                resp = item.getLabel();
                break;
            }
        }
        return resp;
    }

}
