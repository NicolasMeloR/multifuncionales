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
public class TipoUbicacion {

    public List<SelectItem> lista;

    public List<SelectItem> crearLista() {
        lista = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem();

        item.setValue("0");
        item.setLabel("Oficina");
        lista.add(item);
        item = new SelectItem();

        item.setValue("1");
        item.setLabel("Remoto");
        lista.add(item);
        item = new SelectItem();

        item.setValue("2");
        item.setLabel("Isla");
        lista.add(item);
        item = new SelectItem();

        item.setValue("3");
        item.setLabel("Empresarial");
        lista.add(item);
        item = new SelectItem();

        item.setValue("4");
        item.setLabel("Oficina_PR");
        lista.add(item);
        item = new SelectItem();

        item.setValue("5");
        item.setLabel("Oficina_Movil");
        lista.add(item);
        item = new SelectItem();

        item.setValue("6");
        item.setLabel("Banagrario");
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
