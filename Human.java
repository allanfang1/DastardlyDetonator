/**
 * Human object
 */

class Human extends Living{
  int bombCap;
  boolean kickable;
  boolean throwable;
  int crateCap;

  Human(int newHealth, double newX, double newY) {
    super(newHealth, newX, newY);
    bombCap=1;
    kickable=false;
    throwable=false;
    crateCap=0;
  }
}
