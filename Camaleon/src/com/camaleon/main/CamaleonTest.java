/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.main;

import com.camaleon.entities.LoadFileResult;
import com.camaleon.entities.Relation;
import com.camaleon.logic.LoadFile;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author ASUS
 */
public class CamaleonTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        LoadFileResult loadFileResult = LoadFile.loadFile("C:/Users/ASUS/Downloads/test_attr.json");

        if (loadFileResult.getStatus().equals(LoadFileResult.Status.SUCCESS)) {
            Relation relacion = loadFileResult.getRelation();

            System.out.println("L: " + relacion);
        }

    }
}
