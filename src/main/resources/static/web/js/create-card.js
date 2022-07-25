Vue.createApp({
    data() {
      return {
        cardType:null,
        cardColor:null,
      }
    },
    created(){
     
},
    methods:{
      selectCardType($event){
        this.cardType = $event.target.value
      },
      selectCardColor($event){
        this.cardColor = $event.target.value
      },
        createCard(){
          swal({
            title:"¿Deseas realizar esta operación?",
            text: `Crear una tarjeta ${this.cardType} ${this.cardColor}`,
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((isCreated) => {
        if(isCreated){
          axios.post('/api/clients/current/cards', 
            `color=${this.cardColor}&type=${this.cardType}`,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
        .catch(res => swal("No se pudo realizar la operación", res.response.data))
        swal("Transacción completada",{
          icon: "success"
        })
        .then(()=> window.location.href = "cards.html")
        }
        })
            
          },
          cancel(){
            window.location.href = "cards.html"
        }, 
      logout(){
        axios.post('/api/logout')
        .then(res => console.log('signed out!!!'))
        .then(res =>window.location.href = "index.html")
    },
  
  
    },
    computed:{
      isFormFilled(){
        if(this.cardColor && this.cardType)
          return true;  
        else
          return false;
      },
    }
  }).mount('#app')