package rd.ijoic.skinchange

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ijoic.skinchange.R
import kotlinx.android.synthetic.main.act_rd_simple.*
import rd.ijoic.skin.SkinManager

/**
 * Simple activity.
 *
 * @author verstsiu on 2018/9/26
 * @version 1.0
 */
class SimpleActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_rd_simple)
    val skinManager = SkinManager(this)
    var suffix = skinManager.getConfig("holo").suffix ?: "red"

    skinManager.attachStyle("delay", "holo")
    skinManager.init(this)

    action_toggle_skin.setOnClickListener {
      suffix = suffix.toggle("red", "green")

      skinManager.changeSkin(suffix, "holo")
    }
  }

  private fun<T> T.toggle(t1: T, t2: T): T = when (t1) {
    this -> t2
    else -> t1
  }

}