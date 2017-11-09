/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author ysa
 */
public class Const {

    public static class User {
        public static final String SALT = "m@mn0nto1.vN";
    }
    
    public static class Brand {

        public static class ExtEquipment {

            public static final short BE_BOI = 8;
            public static final short SAN_CHOI_NGOAI_TROI = 16;
            public static final short THU_VIEN = 128;

            public static int addBeBoi(int currValue) {
                return currValue | BE_BOI;
            }

            public static int removeBeBoi(int currValue) {
                return currValue & ~BE_BOI;
            }

            public static boolean isBeBoi(int currValue) {
                return (currValue & BE_BOI) == BE_BOI;
            }

            public static int addSanChoiNgoaiTroi(int currValue) {
                return currValue | SAN_CHOI_NGOAI_TROI;
            }

            public static int removeSanChoiNgoaiTroi(int currValue) {
                return currValue & ~SAN_CHOI_NGOAI_TROI;
            }

            public static boolean isSanChoiNgoaiTroi(int currValue) {
                return (currValue & SAN_CHOI_NGOAI_TROI) == SAN_CHOI_NGOAI_TROI;
            }

            public static int addThuVien(int currValue) {
                return currValue | THU_VIEN;
            }

            public static int removeThuVien(int currValue) {
                return currValue & ~THU_VIEN;
            }

            public static boolean isThuVien(int currValue) {
                return (currValue & THU_VIEN) == THU_VIEN;
            }

        }

        public static class ExtService {

            public static final short AN_SANG = 8;
            public static final short DON_MUON = 16;
            public static final short XE_BUS_DUA_DON = 128;

            public static int addAnSang(int currValue) {
                return currValue | AN_SANG;
            }

            public static int removeAnSang(int currValue) {
                return currValue & ~AN_SANG;
            }

            public static boolean isAnSang(int currValue) {
                return (currValue & AN_SANG) == AN_SANG;
            }

            public static int addDonMuon(int currValue) {
                return currValue | DON_MUON;
            }

            public static int removeDonMuon(int currValue) {
                return currValue & ~DON_MUON;
            }

            public static boolean isDonMuon(int currValue) {
                return (currValue & DON_MUON) == DON_MUON;
            }

            public static int addXeBusDuaDon(int currValue) {
                return currValue | XE_BUS_DUA_DON;
            }

            public static int removeXeBusDuaDon(int currValue) {
                return currValue & ~XE_BUS_DUA_DON;
            }

            public static boolean isXeBusDuaDon(int currValue) {
                return (currValue & XE_BUS_DUA_DON) == XE_BUS_DUA_DON;
            }
        }

    }

    public static class School {

        public static class ExtEquipment {

            public static final short BE_BOI = 8;
            public static final short SAN_CHOI_NGOAI_TROI = 16;
            public static final short THU_VIEN = 128;

            public static int addBeBoi(int currValue) {
                return currValue | BE_BOI;
            }

            public static int removeBeBoi(int currValue) {
                return currValue & ~BE_BOI;
            }

            public static boolean isBeBoi(int currValue) {
                return (currValue & BE_BOI) == BE_BOI;
            }

            public static int addSanChoiNgoaiTroi(int currValue) {
                return currValue | SAN_CHOI_NGOAI_TROI;
            }

            public static int removeSanChoiNgoaiTroi(int currValue) {
                return currValue & ~SAN_CHOI_NGOAI_TROI;
            }

            public static boolean isSanChoiNgoaiTroi(int currValue) {
                return (currValue & SAN_CHOI_NGOAI_TROI) == SAN_CHOI_NGOAI_TROI;
            }

            public static int addThuVien(int currValue) {
                return currValue | THU_VIEN;
            }

            public static int removeThuVien(int currValue) {
                return currValue & ~THU_VIEN;
            }

            public static boolean isThuVien(int currValue) {
                return (currValue & THU_VIEN) == THU_VIEN;
            }

        }

        public static class ExtService {

            public static final short AN_SANG = 8;
            public static final short DON_MUON = 16;
            public static final short XE_BUS_DUA_DON = 128;

            public static int addAnSang(int currValue) {
                return currValue | AN_SANG;
            }

            public static int removeAnSang(int currValue) {
                return currValue & ~AN_SANG;
            }

            public static boolean isAnSang(int currValue) {
                return (currValue & AN_SANG) == AN_SANG;
            }

            public static int addDonMuon(int currValue) {
                return currValue | DON_MUON;
            }

            public static int removeDonMuon(int currValue) {
                return currValue & ~DON_MUON;
            }

            public static boolean isDonMuon(int currValue) {
                return (currValue & DON_MUON) == DON_MUON;
            }

            public static int addXeBusDuaDon(int currValue) {
                return currValue | XE_BUS_DUA_DON;
            }

            public static int removeXeBusDuaDon(int currValue) {
                return currValue & ~XE_BUS_DUA_DON;
            }

            public static boolean isXeBusDuaDon(int currValue) {
                return (currValue & XE_BUS_DUA_DON) == XE_BUS_DUA_DON;
            }
        }
    }
}
