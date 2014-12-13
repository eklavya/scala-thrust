import com.github.eklavya.thrust._

import scala.concurrent.ExecutionContext.Implicits.global

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