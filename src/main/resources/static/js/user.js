console.log("user.js loaded");

 const {createApp} = Vue;

 createApp({
     data() {
         return {
             name:"",
             account: "",
             password: "",
             phone:"",
             username: "",
             email: "",
             creUser:"",
             creDate:"",
             updUser:"",
             updDate:""
         }
     },
     methods: {
         reg() {
             axios.post("/user/insert", {
                 account: this.account,
                 password: this.password,
                 name: this.name,
                 phone:this.phone,
                 mail: this.mail,
                 creUser:this.creUser,
                 creDate:this.creDate,
                 updUser:this.updUser,
                 updDate:this.updDate
             }).then(res => {
                     alert("註冊成功");
                     console.log(res.data);
             }).catch(err => {
                     alert("註冊失敗");
                     console.error(err);
                 });
         }
     }
 }).mount("#app");
