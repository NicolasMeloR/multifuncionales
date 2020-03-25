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
public class AplicativoCajero {

    public List<SelectItem> lista;

    public List<SelectItem> crearLista() {
        lista = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem();

        
        
        item.setValue("0");
        item.setLabel("Agilis");
        lista.add(item);
        item = new SelectItem();

        item.setValue("1");
        item.setLabel("PACE");
        lista.add(item);
        item = new SelectItem();

        item.setValue("2");
        item.setLabel("NDC");
        lista.add(item);
        item = new SelectItem();

        item.setValue("3");
        item.setLabel("PAS");
        lista.add(item);
        item = new SelectItem();

        item.setValue("4");
        item.setLabel("EmPower");
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
