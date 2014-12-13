scala-thrust
============

Thrust bindings for Scala

How to use
==========
Point to thrustshell binary in application.conf.

Then use activator run inside example to run.

```scala
import scala.concurrent.ExecutionContext.Implicits.global
import com.github.eklavya.thrust._

object Main extends App {
  Window.create("http://google.com").foreach { w =>
    w.show
    w.maximize
    w.openDevtools
    w.focus(true)
    w.onBlur(() => println("we were blurred"))
    w.onFocus(() => println("we were focused"))
    Menu.create("MyMenu").foreach { m =>
      val i = MenuItem("Item1", _ => println("Item1 was clicked"))
      m.addItem(i)
      m.popup(w)
    }
  }
}
```
