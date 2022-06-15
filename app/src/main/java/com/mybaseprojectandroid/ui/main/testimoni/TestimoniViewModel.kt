package com.mybaseprojectandroid.ui.main.testimoni

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.model.Testimoni

class TestimoniViewModel(val rvVideoTesti : RecyclerView) : ViewModel() {
    class Factory(val rvVideoTesti : RecyclerView) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TestimoniViewModel(rvVideoTesti) as T
        }
    }

    var listTestimoni = ArrayList<Testimoni>()

    fun setData(){
        listTestimoni.add(Testimoni("aEDvBeS7qbg&t=90s","10 MANFAAT BERJALAN KAKI SECARA TERATUR","Video informasi & edukasi yang menjelaskan tentang 10 manfaat yang didapatkan dengan rutin berjalan kaki."))
        listTestimoni.add(Testimoni("ICVybzupZQM","Komunitas Terapi Jalan kaki","Komunitas Miracle Walking, di kota Bandung, mengajak masyarakat, untuk berjalan secara benar. Dengan memahami dan melakukan aktivitas, jalan secara benar, postur tubuh menjadi lebih nyaman, energi lebih lancar, dan potensi perbaikan tubuh, lebih baik."))
        listTestimoni.add(Testimoni("aEDvBeS7qbg&t=90s","Keajaiban Jalan Kaki Bagi Kesehatan","Jalan kaki adalah jenis olahraga yang gampang dilakukan, hemat, dan bisa dilakukan oleh semua orang,\n" +"mulai dari anak-anak hingga mereka yang menginjak usia senja."))
        listTestimoni.add(Testimoni("sRIaQ9Rps-Y","Manfaat Berjalan KAKI || dr. H. Tejo Katon, S.Si, MBA, MM"," Jalan kaki merupakan olahraga sederhana yang dapat dilakukan di mana saja tanpa butuh peralatan apapun. Meski sangat sederhana, jalan kaki setiap hari memiliki manfaat yang luar biasa."))
        listTestimoni.add(Testimoni("14JozrrFnEo","5 MANFAAT SERING BERJALAN KAKI YANG ENGGAK BOLEH DILEWATKAN!","5 MANFAAT SERING BERJALAN KAKI YANG ENGGAK BOLEH DILEWATKAN!"))
        listTestimoni.add(Testimoni("xAIM7hDs7V8","Info Kesehatan, Olahraga Jalan Kaki Lebih Efektif Dari Pada Lari.","Info Kesehatan, Olahraga Jalan Kaki Lebih Efektif Dari Pada Lari."))
        setList()



    }

    private fun setList() {
        val adapterr = TestimoniAdapter(listTestimoni)
        rvVideoTesti.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterr
        }
    }

}