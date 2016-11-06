package com.camaleon.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class LoadFileResult {

    public enum Status {
        SUCCESS, ERROR
    }

    private Status status;
    private Relation relation;
    private List<String> messages;

    public LoadFileResult() {
        messages = new ArrayList<String>();
    }

    public LoadFileResult(Status status, Relation relation, List<String> messages) {
        this.status = status;
        this.relation = relation;
        this.messages = messages;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "LoadFileResult{" + "status=" + status + ", relation=" + relation + ", messages=" + messages + '}';
    }

}
