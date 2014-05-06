/** 
  *  Copyright (c) 2011  Ng Pan Wei
  *  
  *  Permission is hereby granted, free of charge, to any person 
  *  obtaining a copy of this software and associated documentation files 
  *  (the "Software"), to deal in the Software without restriction, 
  *  including without limitation the rights to use, copy, modify, merge, 
  *  publish, distribute, sublicense, and/or sell copies of the Software, 
  *  and to permit persons to whom the Software is furnished to do so, 
  *  subject to the following conditions: 
  *  
  *  The above copyright notice and this permission notice shall be 
  *  included in all copies or substantial portions of the Software. 
  *  
  *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
  *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
  *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
  *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
  *  BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN 
  *  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
  *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
  *  SOFTWARE. 
  */ 
package jShuffler.basic;

public class Target {
	public int owner ;
	public Target(int value) {
		owner = value ;
	}
	public void doProcedure1() {
		f1() ;
		f2() ;
	}
	public void doProcedure2() {
		fa() ;
		fb() ;
	}
	public void doProcedure(int value) {
		if(value==1) doProcedure1() ;
		if(value==2) doProcedure2() ;
	}
	public void f1() {
		print("f1") ;
	}
	public void f2() {
		print("f2") ;
	}
	public void fa() {
		print("fa") ;
	}
	public void fb() {
		print("fb") ;
	}
	protected void print(String func) {
		System.err.println("-"+owner+"::"+func) ;
	}
}
