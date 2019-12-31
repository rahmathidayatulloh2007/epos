/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.lov.bean;

/**
 *
 * @author destri.hs
 */
public interface SelectorListener <T> {
    void itemSelected(String clientId, String widgetVar, T selectedItem);    
}
