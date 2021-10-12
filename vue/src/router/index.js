import { createRouter, createWebHistory } from 'vue-router'
import Login from "@/views/Login";
import AdminLayout from "@/layout/AdminLayout";
import Register from "@/views/Register";
import AdminUser from "@/views/AdminUser"
import FadminLayout from "@/layout/FadminLayout";
import TraderLayout from "@/layout/TraderLayout";
import AdminFactory from "@/views/AdminFactory";
import PersonInfo from "@/views/PersonInfo";
import AdminProductType from "@/views/AdminProductType";
import AdminProduct from "@/views/AdminProduct";
import AdminEquipmentType from "@/views/AdminEquipmentType";
import AdminEquipment from "@/views/AdminEquipment";
import AdminOrder from "@/views/AdminOrder";
import FadminEquipment from "@/views/FadminEquipment";
import FadminOrder from "@/views/FadminOrder";
import FadminScheduling from "@/views/FadminScheduling";
import TraderOrder from "@/views/TraderOrder";
import FadminCapacity from "@/views/FadminCapacity";

const routes = [
  {
    path: '/',
    name: 'Login',
    redirect: "/login",
    component: Login,
    children: [
      {
        path: 'login',
        name: 'Login',
        component: Login,
      }
    ]
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/adminlayout',
    name: 'AdminLayout',
    component: AdminLayout,
    children: [
      {
        path: '/adminuser',
        name: 'AdminUser',
        component: AdminUser
      },
      {
        path: '/adminfactory',
        name: 'AdminFactory',
        component: AdminFactory,
      },
      {
        path: '/personinfo',
        name: 'PersonInfo',
        component: PersonInfo,
      },
      {
        path: '/adminproducttype',
        name: 'AdminProductType',
        component: AdminProductType
      },
      {
        path: '/adminproduct',
        name: 'AdminProduct',
        component: AdminProduct
      },
      {
        path: '/adminequipmenttype',
        name: 'AdminEquipmentType',
        component: AdminEquipmentType
      },
      {
        path: '/adminequipment',
        name: 'AdminEquipment',
        component: AdminEquipment
      },
      {
        path: '/adminorder',
        name: 'AdminOrder',
        component: AdminOrder
      }
    ]
  },
  {
    path: '/fadminlayout',
    name: 'FadminLayout',
    component: FadminLayout,
    children: [
      {
        path: '/fadminequipment',
        name: 'Fadminequipment',
        component: FadminEquipment
      },
      {
        path: '/fadminorder',
        name: 'Fadminorder',
        component: FadminOrder
      },
      {
        path: '/fadminscheduling',
        name: 'FadminScheduling',
        component: FadminScheduling
      },
      {
        path: '/fadmincapacity',
        name: 'FadminCapacity',
        component: FadminCapacity
      }


    ]
  },
  {
    path: '/traderlayout',
    name: 'TraderLayout',
    component: TraderLayout,
    children: [
      {
        path: '/traderorder',
        name: 'TraderOrder',
        component: TraderOrder
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
