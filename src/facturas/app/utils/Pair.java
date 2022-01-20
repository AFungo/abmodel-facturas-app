/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import java.util.Objects;

/**
 *
 * @author Agustin
 * @param <A>
 * @param <B>
 */
public class Pair<A,B> {
    A a;
    B b;
    
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }
    
    public A getFst() {
        return a;
    }
    
    public B getSnd() {
        return b;
    }
    
    public void setFst(A a) {
        this.a = a;
    }
    
    public void setSnd(B b) {
        this.b = b;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        
        Pair<A,B> other = (Pair<A,B>)o;
        return a.equals(other.getFst()) && b.equals(other.getSnd());
    }
    
    @Override
    public String toString() {
        return "(" + a.toString() + ", " + b.toString() + ")";
    }
    
}
