package core;

/**
 * Created by deanto on 04.05.14.
 */
public class TokenComparer{
    public static boolean Equals(Token incoming, Token template){
        CheckException e = new CheckException();
        try{
            // check names
            if (!incoming.GetName().equals(template.GetName()))
                throw e;

            // check properties
            for (int i=0;i<template.get_properties().size();i++){
                // find property in incoming token
                boolean found = false;
                for (int j=0;j<incoming.get_properties().size();j++){
                    if (template.get_properties().get(i).getName().equals(incoming.get_properties().get(j).getName())){
                        found = true;

                        // check values of properties
                        if (!template.get_properties().get(i).getValue().getStringValue().equals(incoming.get_properties().get(j).getValue().getStringValue()))
                            throw e;
                    }
                    if (found) break;
                }

                if (!found) throw e;
            }

        }
        catch (CheckException a){
            return false;
        }
        return true;
    }

    private static class CheckException extends Exception{
    }
}
