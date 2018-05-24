/**
 * Human object
 */

class Human extends Living{
  int bombCap;
  boolean kickable;
  boolean throwable;
  int crateCap;
  
  Human(){
    super.health=3;
    bombCap=1;
    kickable=false;
    throwable=false;
    crateCap=0;
  }
  
}