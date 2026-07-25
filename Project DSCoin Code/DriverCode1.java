

import DSCoinPackage.*;
import HelperClasses.*;
import java.util.*;
public class DriverCode1 {

    public static void main(String args[]) {

        // Expected output given below 
        
        DSCoin_Honest DSObj = new DSCoin_Honest();
        DSObj.pendingTransactions = new TransactionQueue();
        DSObj.bChain = new BlockChain_Honest();
        Boolean correct = true;
        DSObj.bChain.tr_count = 4;
        Members m1 = new Members();
        Members m2 = new Members();
        Members m3 = new Members();
        Members m4 = new Members();
        m1.UID = "101";
        m2.UID = "102";
        m3.UID = "103";
        m4.UID = "104";
        m1.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        m2.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        m3.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        m4.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        m1.in_process_trans = new Transaction[100];
        m2.in_process_trans = new Transaction[100];
        m3.in_process_trans = new Transaction[100];
        m4.in_process_trans = new Transaction[100];
        DSObj.memberlist = new Members[4];
        DSObj.memberlist[0] = m1;
        DSObj.memberlist[1] = m2;
        DSObj.memberlist[2] = m3;
        DSObj.memberlist[3] = m4;

        Moderator mod = new Moderator();
        try {
            mod.initializeDSCoin(DSObj, 8);
        } catch (Exception e) {
            System.out.println(e);
        }

        m1.initiateCoinsend("102", DSObj);
        m3.initiateCoinsend("102", DSObj);
        m3.initiateCoinsend("102", DSObj);
        Pair<List<Pair<String, String>>,List<Pair<String, String>>> temp = new Pair<List<Pair<String, String>>, List<Pair<String, String>>>(null,null);
        try {
            m2.MineCoin(DSObj);
            temp = m1.finalizeCoinsend(DSObj.bChain.lastBlock.trarray[0],DSObj);
            temp = m3.finalizeCoinsend(DSObj.bChain.lastBlock.trarray[2],DSObj);
        } catch (Exception e) {
        }

        m2.initiateCoinsend("101", DSObj);
        Transaction fake = new Transaction();
        fake.Source = m1;
        fake.Destination = m3;
        fake.coinsrc_block = DSObj.bChain.lastBlock;
        fake.coinID = "100000";
        DSObj.pendingTransactions.AddTransactions(fake);
        m2.initiateCoinsend("101", DSObj);
        m4.initiateCoinsend("101", DSObj);
        try {
            m3.MineCoin(DSObj);
            Pair<List<Pair<String, String>>,List<Pair<String, String>>> temp1 = m2.finalizeCoinsend(DSObj.bChain.lastBlock.trarray[0], DSObj);
            temp1 = m2.finalizeCoinsend(DSObj.bChain.lastBlock.trarray[1], DSObj);
            temp1 = m4.finalizeCoinsend(DSObj.bChain.lastBlock.trarray[2], DSObj);
            temp = m3.finalizeCoinsend(DSObj.bChain.lastBlock.previous.trarray[1],DSObj);
        } catch (Exception e) {
            System.out.println(e);
        }

        

        correct &= DSObj.bChain.lastBlock.dgst.equals("00004F4D8749BFFE9E1BEF4152F6849BCD0A529B463FF0D409B338AFABF690C8");
        
        try {
            Transaction tobj = new Transaction();
            tobj.Source = m3;
            tobj.Destination = m2;
            tobj.coinID = "100002";
            Pair<List<Pair<String, String>>,List<Pair<String, String>>> p = temp;

            correct &= p.get_first().get(0).get_first().equals("3A767231549E643B899B511E0BB1BD438C08A47D0E145B3A77CAFC4E5EF91DEF");
            correct &= p.get_first().get(1).get_first().equals("A86B49EA9F5245DEA4949D3815140D8CF5B1127C6EBB9B54197D87A5B0579C11");
            correct &= p.get_first().get(2).get_first().equals("036462EC76F2BEDA0CE4822E8747FEC9B69671359E7E3D2BD96485748360FD62");
            correct &= p.get_first().get(0).get_second().equals("B8444CE1F8BD43465F42E29A6E7335A29A12EAF96D4249E710098EE52A2027F2");
            correct &= p.get_first().get(1).get_second().equals("3A0E5A405EFB47DB8EB53FEF60C428FD85964E1C2E090DBC87000FEF02A04F0D");
            
            correct &= p.get_second().get(0).get_first().equals("00001DBCB949AF5097FE4F2D4CF1F545624ED5646931466170FCFB771D584D9C");
            correct &= p.get_second().get(1).get_first().equals("00009A7F99D2D09244E99D1F55AD29B49D335D9254A2EC682341ECFBC905AF4C");
            correct &= p.get_second().get(2).get_first().equals("00004F4D8749BFFE9E1BEF4152F6849BCD0A529B463FF0D409B338AFABF690C8");
            correct &= p.get_second().get(1).get_second().equals("00001DBCB949AF5097FE4F2D4CF1F545624ED5646931466170FCFB771D584D9C#036462EC76F2BEDA0CE4822E8747FEC9B69671359E7E3D2BD96485748360FD62#1000052544");
            correct &= p.get_second().get(2).get_second().equals("00009A7F99D2D09244E99D1F55AD29B49D335D9254A2EC682341ECFBC905AF4C#1CE221942486B63BD73A3D89082EF56FB5F0B2DB6B0D87EA8D81557B8A26D4AD#1000188337");
            
            if(correct == true){
                System.out.println("All test cases pass!");
            }
            else{
                System.out.println("Some test case is incorrect!");
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    









        DSCoin_Malicious DSObj2 = new DSCoin_Malicious();
        DSObj2.pendingTransactions = new TransactionQueue();
        DSObj2.bChain = new BlockChain_Malicious();
        DSObj2.bChain.lastBlocksList = new TransactionBlock[100];
        Boolean correct2 = true;
        DSObj2.bChain.tr_count = 8;
        Members M1 = new Members();
        Members M2 = new Members();
        Members M3 = new Members();
        Members M4 = new Members();
        Members M5 = new Members();
        Members M6 = new Members();
        M1.UID = "201";
        M2.UID = "202";
        M3.UID = "203";
        M4.UID = "204";
        M5.UID = "205";
        M6.UID = "206";
        M1.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M2.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M3.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M4.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M5.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M6.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M1.in_process_trans = new Transaction[100];
        M2.in_process_trans = new Transaction[100];
        M3.in_process_trans = new Transaction[100];
        M4.in_process_trans = new Transaction[100];
        M5.in_process_trans = new Transaction[100];
        M6.in_process_trans = new Transaction[100];
        DSObj2.memberlist = new Members[6];
        DSObj2.memberlist[0] = M1;
        DSObj2.memberlist[1] = M2;
        DSObj2.memberlist[2] = M3;
        DSObj2.memberlist[3] = M4;
        DSObj2.memberlist[4] = M5;
        DSObj2.memberlist[5] = M6;
        
        try {
            mod.initializeDSCoin(DSObj2, 24);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(DSObj2.bChain.lastBlocksList[0].dgst);
        System.out.println(DSObj2.bChain.lastBlocksList[0].nonce);



        Transaction T1 = new Transaction();
        Transaction T2 = new Transaction();
        Transaction T3 = new Transaction();
        Transaction T4 = new Transaction();
        Transaction T5 = new Transaction();
        Transaction T6 = new Transaction();
        Transaction T7 = new Transaction();
        Transaction T8 = new Transaction();

        T1.coinID = DSObj2.memberlist[0].mycoins.get(0).get_first();
        T1.Source = DSObj2.memberlist[0];
        T1.Destination = DSObj2.memberlist[1];
        T1.coinsrc_block = DSObj2.memberlist[0].mycoins.get(0).get_second();
        DSObj2.pendingTransactions.AddTransactions(T1);

        T2.coinID = DSObj2.memberlist[2].mycoins.get(0).get_first();
        T2.Source = DSObj2.memberlist[2];
        T2.Destination = DSObj2.memberlist[1];
        T2.coinsrc_block = DSObj2.memberlist[2].mycoins.get(0).get_second();
        DSObj2.pendingTransactions.AddTransactions(T2);

        T3.coinID = DSObj2.memberlist[2].mycoins.get(1).get_first();
        T3.Source = DSObj2.memberlist[2];
        T3.Destination = DSObj2.memberlist[1];
        T3.coinsrc_block = DSObj2.memberlist[2].mycoins.get(1).get_second();
        DSObj2.pendingTransactions.AddTransactions(T3);

        T4.coinID = DSObj2.memberlist[3].mycoins.get(0).get_first();
        T4.Source = DSObj2.memberlist[3];
        T4.Destination = DSObj2.memberlist[4];
        T4.coinsrc_block = DSObj2.memberlist[3].mycoins.get(0).get_second();
        DSObj2.pendingTransactions.AddTransactions(T4);

        T5.coinID = DSObj2.memberlist[3].mycoins.get(1).get_first();
        T5.Source = DSObj2.memberlist[3];
        T5.Destination = DSObj2.memberlist[4];
        T5.coinsrc_block = DSObj2.memberlist[3].mycoins.get(1).get_second();
        DSObj2.pendingTransactions.AddTransactions(T5);

        T6.coinID = DSObj2.memberlist[3].mycoins.get(2).get_first();
        T6.Source = DSObj2.memberlist[3];
        T6.Destination = DSObj2.memberlist[5];
        T6.coinsrc_block = DSObj2.memberlist[3].mycoins.get(2).get_second();
        DSObj2.pendingTransactions.AddTransactions(T6);

        T7.coinID = DSObj2.memberlist[0].mycoins.get(1).get_first();
        T7.Source = DSObj2.memberlist[0];
        T7.Destination = DSObj2.memberlist[5];
        T7.coinsrc_block = DSObj2.memberlist[0].mycoins.get(1).get_second();
        DSObj2.pendingTransactions.AddTransactions(T7);

        T8.coinID = DSObj2.memberlist[2].mycoins.get(2).get_first();
        T8.Source = DSObj2.memberlist[2];
        T8.Destination = DSObj2.memberlist[4];
        T8.coinsrc_block = DSObj2.memberlist[2].mycoins.get(2).get_second();
        DSObj2.pendingTransactions.AddTransactions(T8);

       
        try {
            M3.MineCoin(DSObj2);
        } catch (Exception e) {
        }
        System.out.println(DSObj2.bChain.lastBlocksList[0].dgst);
        System.out.println(DSObj2.bChain.lastBlocksList[0].nonce);
    


        DSCoin_Malicious DSObj3 = new DSCoin_Malicious();
        DSObj3.pendingTransactions = new TransactionQueue();
        DSObj3.bChain = new BlockChain_Malicious();
        DSObj3.bChain.lastBlocksList = new TransactionBlock[100];
        DSObj3.bChain.tr_count = 8;
        Members M_1 = new Members();
        Members M_2 = new Members();
        Members M_3 = new Members();
        Members M_4 = new Members();
        Members M_5 = new Members();
        Members M_6 = new Members();
        M_1.UID = "201";
        M_2.UID = "202";
        M_3.UID = "203";
        M_4.UID = "204";
        M_5.UID = "205";
        M_6.UID = "206";
        M_1.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M_2.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M_3.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M_4.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M_5.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M_6.mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        M_1.in_process_trans = new Transaction[100];
        M_2.in_process_trans = new Transaction[100];
        M_3.in_process_trans = new Transaction[100];
        M_4.in_process_trans = new Transaction[100];
        M_5.in_process_trans = new Transaction[100];
        M_6.in_process_trans = new Transaction[100];
        DSObj3.memberlist = new Members[6];
        DSObj3.memberlist[0] = M_1;
        DSObj3.memberlist[1] = M_2;
        DSObj3.memberlist[2] = M_3;
        DSObj3.memberlist[3] = M_4;
        DSObj3.memberlist[4] = M_5;
        DSObj3.memberlist[5] = M_6;
        
        try {
            mod.initializeDSCoin(DSObj3, 24);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(DSObj3.bChain.lastBlocksList[0].dgst);
        System.out.println(DSObj3.bChain.lastBlocksList[0].nonce);
	System.out.println(printDSObj(DSObj3));




    
    DSCoin_Honest obj = makeHonestObj(4, 8);
	Moderator Mood = new Moderator();
	Mood.initializeDSCoin(obj, 32);
	for (int i = 0; i < 10; i ++){
	    obj.memberlist[i%4].initiateCoinsend(obj.memberlist[(i+1)%4].UID, obj);
	}
	try {
	    obj.memberlist[0].MineCoin(obj);
	}
	catch (Exception e) {
	    System.out.println("My name is kira yoshikage, I am 33 years old");
	}
	for (int i = 0; i < 8; i ++){
	    if (obj.bChain.lastBlock.trarray[i].Source != null){
		try{
		    Pair<List<Pair<String, String>>, List<Pair<String, String>>> lists =  obj.bChain.lastBlock.trarray[i].Source.finalizeCoinsend(obj.bChain.lastBlock.trarray[i], obj);
		    System.out.println(printLists(lists));
		}
		catch (Exception e) {
		    System.out.println("My name is kira yoshikage, I am 33 years old");
		}
	    }
	}
	System.out.println(printDSObj(obj));




    //////////////////////////////////////
    CRF objjj = new CRF(64);
		Moderator moddderator = new Moderator();
		
		Members M_1_n = new Members();
		Members M_2_n = new Members();
		Members M_3_n = new Members();
		Members M_4_n = new Members();
		M_1_n.UID = "101";
		M_2_n.UID = "102";
		M_3_n.UID = "103";
		M_4_n.UID = "104";
		
		M_1_n.mycoins = new ArrayList<>();
		M_2_n.mycoins = new ArrayList<>();
		M_3_n.mycoins = new ArrayList<>();
		M_4_n.mycoins = new ArrayList<>();
		
		M_1_n.in_process_trans = new Transaction[0];
		M_2_n.in_process_trans = new Transaction[0];
		M_3_n.in_process_trans = new Transaction[0];
		M_4_n.in_process_trans = new Transaction[0];
		
		DSCoin_Honest DSObj_11 = new DSCoin_Honest();
		DSObj_11.memberlist = new Members[]{M_1_n, M_2_n, M_3_n, M_4_n};
		DSObj_11.pendingTransactions = new TransactionQueue();
		DSObj_11.bChain = new BlockChain_Honest();
		DSObj_11.bChain.tr_count = 4;
		
		
		moddderator.initializeDSCoin(DSObj_11, 8);
		
		
		TransactionBlock tb1 = DSObj_11.bChain.lastBlock.previous;
		
		System.out.println("----TransactionBlock 1 (tb1): (Moderator)----\n");
		
		System.out.println("tb1.dgst:  " + tb1.dgst.equals("00002D934C71EEF934E902CF1C10B5F7ADF12D8A96B4D00F5C4A23F2776F582B"));
		System.out.println("tb1.nonce:  " + tb1.nonce.equals("1000004711"));
		System.out.println("tb1.trsummary:  " + tb1.trsummary.equals("CB201A28476E5AE23A91669F7E5D90ED40C99DFC816E881F2119C7A14A64EB1C"));
		boolean bool = tb1.previous == null;
		System.out.println("tb1.previous:  " + bool + "\n" + "--------------------------------------------------");
		
		System.out.println("--------------------------------------------------");
		
		
		TransactionBlock tb2 = DSObj_11.bChain.lastBlock;
		
		System.out.println("----TransactionBlock 2 (tb2): (Moderator)----\n");
		
		System.out.println("tb2.dgst:  " + tb2.dgst.equals("00001DBCB949AF5097FE4F2D4CF1F545624ED5646931466170FCFB771D584D9C"));
		System.out.println("tb2.nonce:  " + tb2.nonce.equals("1000051123"));
		System.out.println("tb2.trsummary:  " + tb2.trsummary.equals("0BABE4EFB2BD7F39D999E8809DDFCC649E6B8B6CA47ECD907D6784A4DA0DEDCB"));
		System.out.println("tb2.previous:  " + tb2.previous.dgst.equals("00002D934C71EEF934E902CF1C10B5F7ADF12D8A96B4D00F5C4A23F2776F582B") + "\n" + "--------------------------------------------------");
		System.out.println("--------------------------------------------------");
		
		System.out.println("-----After tb1 and tb2-----\n");
		
		System.out.println("M_1_n.mycoins:  " + (M_1_n.mycoins.size() == 2 && M_1_n.mycoins.get(0).get_first().equals("100000") && M_1_n.mycoins.get(1).get_first().equals("100004")));
		System.out.println("M_2_n.mycoins:  " + (M_2_n.mycoins.size() == 2 && M_2_n.mycoins.get(0).get_first().equals("100001") && M_2_n.mycoins.get(1).get_first().equals("100005")));
		System.out.println("M_3_n.mycoins:  " + (M_3_n.mycoins.size() == 2 && M_3_n.mycoins.get(0).get_first().equals("100002") && M_3_n.mycoins.get(1).get_first().equals("100006")));
		System.out.println("M_4_n.mycoins:  " + (M_4_n.mycoins.size() == 2 && M_4_n.mycoins.get(0).get_first().equals("100003") && M_4_n.mycoins.get(1).get_first().equals("100007")));
		System.out.println("--------------------------------------------------");
		
		System.out.println("DSObj_11.latestCoinID (= 100007):  " + DSObj_11.latestCoinID.equals("100007"));
		System.out.println("--------------------------------------------------");
		
		System.out.println("DSObj_11.pendingTransactions (= empty):  " + (DSObj_11.pendingTransactions.numTransactions == 0));
		System.out.println("--------------------------------------------------");
		System.out.println("--------------------------------------------------");


		
		M_1_n.initiateCoinsend("102", DSObj_11);
		M_3_n.initiateCoinsend("102", DSObj_11);
		M_3_n.initiateCoinsend("102", DSObj_11);
		try {
			M_2_n.MineCoin(DSObj_11);
			Pair<List<Pair<String, String>>,List<Pair<String, String>>> tep_2 = M_1_n.finalizeCoinsend(DSObj_11.bChain.lastBlock.trarray[0],DSObj_11);
			tep_2 = M_3_n.finalizeCoinsend(DSObj_11.bChain.lastBlock.trarray[1],DSObj_11);
			tep_2 = M_3_n.finalizeCoinsend(DSObj_11.bChain.lastBlock.trarray[2],DSObj_11);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TransactionBlock tb3 = DSObj_11.bChain.lastBlock;
		
		System.out.println("\n \n----TransactionBlock 3 (tb3): (Mined by M_2_n (UID: 102))----\n");
		
		System.out.println("tb3.dgst:  " + tb3.dgst.equals("00009A7F99D2D09244E99D1F55AD29B49D335D9254A2EC682341ECFBC905AF4C"));
		System.out.println("tb3.nonce:  " + tb3.nonce.equals("1000052544"));
		System.out.println("tb3.trsummary:  " + tb3.trsummary.equals("036462EC76F2BEDA0CE4822E8747FEC9B69671359E7E3D2BD96485748360FD62"));
		System.out.println("tb3.previous:  " + tb3.previous.dgst.equals("00001DBCB949AF5097FE4F2D4CF1F545624ED5646931466170FCFB771D584D9C") + "\n" + "--------------------------------------------------");
		
		System.out.println("M_1_n.mycoins:  " + (M_1_n.mycoins.size() == 1 && M_1_n.mycoins.get(0).get_first().equals("100004")));
		System.out.println("M_2_n.mycoins:  " + (M_2_n.mycoins.size() == 6 && M_2_n.mycoins.get(0).get_first().equals("100000") && M_2_n.mycoins.get(1).get_first().equals("100001") && M_2_n.mycoins.get(2).get_first().equals("100002") && M_2_n.mycoins.get(3).get_first().equals("100005") && M_2_n.mycoins.get(4).get_first().equals("100006") && M_2_n.mycoins.get(5).get_first().equals("100008")));
		System.out.println("M_3_n.mycoins:  " + (M_3_n.mycoins.size() == 0));
		System.out.println("M_4_n.mycoins:  " + (M_4_n.mycoins.size() == 2 && M_4_n.mycoins.get(0).get_first().equals("100003") && M_4_n.mycoins.get(1).get_first().equals("100007")));
		System.out.println("--------------------------------------------------");
		
		System.out.println("DSObj_11.latestCoinID (= 100008):  " + DSObj_11.latestCoinID.equals("100008"));
		System.out.println("--------------------------------------------------");
		
		System.out.println("DSObj_11.pendingTransactions (= empty):  " + (DSObj_11.pendingTransactions.numTransactions == 0));
		System.out.println("--------------------------------------------------");
		System.out.println("--------------------------------------------------");



		M_2_n.initiateCoinsend("101", DSObj_11);
		Transaction fakeeee = new Transaction();
		fakeeee.Source = M_1_n;
		fakeeee.Destination = M_3_n;
		fakeeee.coinsrc_block = DSObj_11.bChain.lastBlock;
		fakeeee.coinID = "100000";
		DSObj_11.pendingTransactions.AddTransactions(fakeeee);
		M_2_n.initiateCoinsend("101", DSObj_11);
		M_4_n.initiateCoinsend("101", DSObj_11);
		try {
			M_3_n.MineCoin(DSObj_11);
			Pair<List<Pair<String, String>>,List<Pair<String, String>>> temp1 = M_2_n.finalizeCoinsend(DSObj_11.bChain.lastBlock.trarray[0], DSObj_11);
			temp1 = M_2_n.finalizeCoinsend(DSObj_11.bChain.lastBlock.trarray[1], DSObj_11);
			temp1 = M_4_n.finalizeCoinsend(DSObj_11.bChain.lastBlock.trarray[2], DSObj_11);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		TransactionBlock tb4 = DSObj_11.bChain.lastBlock;
		
		System.out.println("\n \n----TransactionBlock 4 (tb4): (Mined by M_3_n (UID: 103))----\n");
		
		System.out.println("tb4.dgst:  " + tb4.dgst.equals("00004F4D8749BFFE9E1BEF4152F6849BCD0A529B463FF0D409B338AFABF690C8"));
		System.out.println("tb4.nonce:  " + tb4.nonce.equals("1000188337"));
		System.out.println("tb4.trsummary:  " + tb4.trsummary.equals("1CE221942486B63BD73A3D89082EF56FB5F0B2DB6B0D87EA8D81557B8A26D4AD"));
		System.out.println("tb4.previous:  " + tb4.previous.dgst.equals("00009A7F99D2D09244E99D1F55AD29B49D335D9254A2EC682341ECFBC905AF4C") + "\n" + "--------------------------------------------------");
		
		System.out.println("M_1_n.mycoins:  " + (M_1_n.mycoins.size() == 4 && M_1_n.mycoins.get(0).get_first().equals("100000") && M_1_n.mycoins.get(1).get_first().equals("100001") && M_1_n.mycoins.get(2).get_first().equals("100003") && M_1_n.mycoins.get(3).get_first().equals("100004")));
		System.out.println("M_2_n.mycoins:  " + (M_2_n.mycoins.size() == 4 && M_2_n.mycoins.get(0).get_first().equals("100002") && M_2_n.mycoins.get(1).get_first().equals("100005") && M_2_n.mycoins.get(2).get_first().equals("100006") && M_2_n.mycoins.get(3).get_first().equals("100008")));
		System.out.println("M_3_n.mycoins:  " + (M_3_n.mycoins.size() == 1 && M_3_n.mycoins.get(0).get_first().equals("100009")));
		System.out.println("M_4_n.mycoins:  " + (M_4_n.mycoins.size() == 1 && M_4_n.mycoins.get(0).get_first().equals("100007")));
		System.out.println("--------------------------------------------------");
		
		System.out.println("DSObj_11.latestCoinID (= 100009):  " + DSObj_11.latestCoinID.equals("100009"));
		System.out.println("--------------------------------------------------");
		
		System.out.println("DSObj_11.pendingTransactions (= empty):  " + (DSObj_11.pendingTransactions.numTransactions == 0));
		System.out.println("--------------------------------------------------");
		System.out.println("--------------------------------------------------");
		




        /////////////public static void main(String args[]) {
            DSCoin_Honest obj_7 = createObj(4, 4);

            Moderator Mooood = new Moderator();
            Mooood.initializeDSCoin(obj_7, 8);
        
            System.out.println("AFTER DISTRIBUTION BY MOD-");
            System.out.println("Nonce is " + obj_7.bChain.lastBlock.nonce);
            System.out.println("Dgst is " + obj_7.bChain.lastBlock.dgst);
            System.out.println("Coins of 101: ");
            System.out.println(obj_7.memberlist[0].mycoins.get(0).get_first());
            System.out.println(obj_7.memberlist[0].mycoins.get(1).get_first());
            System.out.println("Coins of 102: ");
            System.out.println(obj_7.memberlist[1].mycoins.get(0).get_first());
            System.out.println(obj_7.memberlist[1].mycoins.get(1).get_first());
            System.out.println("Coins of 103: ");
            System.out.println(obj_7.memberlist[2].mycoins.get(0).get_first());
            System.out.println(obj_7.memberlist[2].mycoins.get(1).get_first());
            System.out.println("Coins of 104: ");
            System.out.println(obj_7.memberlist[3].mycoins.get(0).get_first());
            System.out.println(obj_7.memberlist[3].mycoins.get(1).get_first());
            System.out.println();
        
            Transaction t = new Transaction();
            t.coinID = "100000";
            t.Source = obj_7.memberlist[0];
            t.Destination = obj_7.memberlist[2];
            t.coinsrc_block = obj_7.bChain.lastBlock.previous.previous;
        
            obj_7.memberlist[0].initiateCoinsend(obj_7.memberlist[1].UID, obj_7);
            obj_7.pendingTransactions.AddTransactions(t);
            obj_7.memberlist[2].initiateCoinsend(obj_7.memberlist[1].UID, obj_7);
            obj_7.memberlist[2].initiateCoinsend(obj_7.memberlist[1].UID, obj_7);
            obj_7.memberlist[1].MineCoin(obj_7);
            try {
                obj_7.memberlist[0].finalizeCoinsend(obj_7.bChain.lastBlock.trarray[0], obj_7);
                obj_7.memberlist[2].finalizeCoinsend(obj_7.bChain.lastBlock.trarray[1], obj_7);
                obj_7.memberlist[2].finalizeCoinsend(obj_7.bChain.lastBlock.trarray[2], obj_7);
            } catch (MissingTransactionException e) {}
        
            System.out.println("AFTER 1ST SET OF TRANSACTIONS-");
            System.out.println("Nonce is " + obj_7.bChain.lastBlock.nonce);
            System.out.println("Dgst is " + obj_7.bChain.lastBlock.dgst);
            System.out.println("Coins of 101: ");
            System.out.println(obj_7.memberlist[0].mycoins.get(0).get_first());
            System.out.println("Coins of 102: ");
            System.out.println(obj_7.memberlist[1].mycoins.get(0).get_first());
            System.out.println(obj_7.memberlist[1].mycoins.get(1).get_first());
            System.out.println(obj_7.memberlist[1].mycoins.get(2).get_first());
            System.out.println(obj_7.memberlist[1].mycoins.get(3).get_first());
            System.out.println(obj_7.memberlist[1].mycoins.get(4).get_first());
            System.out.println(obj_7.memberlist[1].mycoins.get(5).get_first());
            System.out.println("Coins of 103: ");
            if (obj_7.memberlist[2].mycoins.size() == 0) {
                System.out.println("None");
            } else {
                System.out.println("WRONG");
            }
            System.out.println("Coins of 104: ");
            System.out.println(obj_7.memberlist[3].mycoins.get(0).get_first());
            System.out.println(obj_7.memberlist[3].mycoins.get(1).get_first());
            System.out.println();
        
            obj_7.memberlist[1].initiateCoinsend(obj_7.memberlist[0].UID, obj_7);
            obj_7.memberlist[1].initiateCoinsend(obj_7.memberlist[0].UID, obj_7);
            obj_7.memberlist[3].initiateCoinsend(obj_7.memberlist[0].UID, obj_7);
            obj_7.memberlist[2].MineCoin(obj_7);
            try {
                obj_7.memberlist[1].finalizeCoinsend(obj_7.bChain.lastBlock.trarray[0], obj_7);
                obj_7.memberlist[1].finalizeCoinsend(obj_7.bChain.lastBlock.trarray[1], obj_7);
                obj_7.memberlist[3].finalizeCoinsend(obj_7.bChain.lastBlock.trarray[2], obj_7);
            } catch (MissingTransactionException e) {}
        
            System.out.println("AFTER 2ND SET OF TRANSACTIONS-");
            System.out.println("Nonce is " + obj_7.bChain.lastBlock.nonce);
            System.out.println("Dgst is " + obj_7.bChain.lastBlock.dgst);
            System.out.println("Coins of 101: ");
            System.out.println(obj_7.memberlist[0].mycoins.get(0).get_first());
            System.out.println(obj_7.memberlist[0].mycoins.get(1).get_first());
            System.out.println(obj_7.memberlist[0].mycoins.get(2).get_first());
            System.out.println(obj_7.memberlist[0].mycoins.get(3).get_first());
            System.out.println("Coins of 102: ");
            System.out.println(obj_7.memberlist[1].mycoins.get(0).get_first());
            System.out.println(obj_7.memberlist[1].mycoins.get(1).get_first());
            System.out.println(obj_7.memberlist[1].mycoins.get(2).get_first());
            System.out.println(obj_7.memberlist[1].mycoins.get(3).get_first());
            System.out.println("Coins of 103: ");
            System.out.println(obj_7.memberlist[2].mycoins.get(0).get_first());
            System.out.println("Coins of 104: ");
            System.out.println(obj_7.memberlist[3].mycoins.get(0).get_first());
            
        
            



    }

    public static DSCoin_Honest createObj (int numOfMembers, int trcount){
        DSCoin_Honest obj_7 = new DSCoin_Honest();
        obj_7.pendingTransactions = new TransactionQueue();
        obj_7.memberlist = new Members[numOfMembers];
        for (int i = 0; i < numOfMembers; i ++){
            obj_7.memberlist[i] = new Members();
            obj_7.memberlist[i].UID = Integer.toString(i+101);
            obj_7.memberlist[i].mycoins = new ArrayList<Pair<String,TransactionBlock>>();
        }
        obj_7.bChain = new BlockChain_Honest();
        obj_7.bChain.tr_count = trcount;
        return obj_7;
    }
    
    public static String printMember (Members m){
    	String s = "Member Name : ";
    	s += m.UID + "\n Member Coins \n";
    	for (Pair<String, TransactionBlock> p : m.mycoins){
    	    s += p.first + " ";
    	}
	s += "\n";
    	return s;
    }
    public static String printBlock (TransactionBlock b){
	String s = "Block Summary: ";
	s += b.trsummary + "\n Nonce ";
	if (b.nonce != null){
	    s += b.nonce + " \n dgst ";
	}
	else {
	    s += "null \n dgst ";
	}
	if (b.dgst != null){
	    s += b.dgst + "\n";
	}
	else {
	    s += "null \n";
	}
	return s;
    }
    public static String printBlockChain (BlockChain_Honest c){
	String s = "Block Chain Honest \n ";
	TransactionBlock b = c.lastBlock;
	for (; b != null; b = b.previous){
	    s += printBlock(b) + "\n";
	}
	return s;
    }
    public static String printBlockChain (BlockChain_Malicious c){
	String s = "Block Chain Malicious \n";
	List<TransactionBlock> l = new ArrayList<>();
	for (int i = 0; i < c.lastBlocksList.length; i ++){
	    if (c.lastBlocksList[i] != null){
		l.add(c.lastBlocksList[i]);
	    }
	}
	for (int i = 1; i < l.size(); i ++){
	    for (int j = i; j > 0 && l.get(j).trsummary.compareTo(l.get(j-1).trsummary) <= 0; j --){
		TransactionBlock temp = l.get(j);
		l.set(j, l.get(j-1));
		l.set(j-1, temp);
	    }
	}
	for (int i = 0; i < l.size(); i ++){
	    for (TransactionBlock b = l.get(i); b != null; b = b.previous){
		s += printBlock(b) + "\n";
	    }
	}
	return s;
    }
    public static String printDSObj (DSCoin_Honest o){
	String s = "Honest Obj latest coin : ";
	s += o.latestCoinID + " \n Block Chain : \n";
	s += printBlockChain(o.bChain) + "memberlist \n";
	for (int i = 0; i < o.memberlist.length; i ++){
	    s += printMember(o.memberlist[i]);
	}
	return s;
    }
    public static String printDSObj (DSCoin_Malicious o){
	String s = "Malicious Obj latest coin : ";
	s += o.latestCoinID + " \n Block Chain : \n";
	s += printBlockChain(o.bChain) + "memberlist \n";
	for (int i = 0; i < o.memberlist.length; i ++){
	    s += printMember(o.memberlist[i]);
	}
	return s;
    }

    public static String printLists (Pair<List<Pair<String, String>>, List<Pair<String, String>>> lists){
        String s = "Lists: \n";
        for (int i = 0; i < lists.first.size(); i ++){
            s += lists.first.get(i).first +  lists.first.get(i).second;
        }
        for (int i = 0; i < lists.second.size(); i ++){
            s += lists.second.get(i).first +  lists.second.get(i).second;
        }
        return s;
        }
    
        public static DSCoin_Honest makeHonestObj (int memnum, int trcount){
        DSCoin_Honest obj = new DSCoin_Honest();
        obj.pendingTransactions = new TransactionQueue();
        obj.memberlist = new Members[memnum];
        for (int i = 0; i < memnum; i ++){
            obj.memberlist[i] = new Members();
            obj.memberlist[i].UID = "Member " + Integer.toString(i);
            obj.memberlist[i].in_process_trans = new Transaction[100];
            obj.memberlist[i].mycoins = new ArrayList<>();

        }
        obj.bChain = new BlockChain_Honest();
        obj.bChain.tr_count = trcount;
        return obj;
        }
}


// Expected Output of the code

// All test cases pass!
// 00000A8BAD2C26E5A57CDE338C11BF2DD5CA86A49403F4048730418B086ED499
// 1000002168
// 00007911E2244B3A99271BC2B7797CD763E67600A8160F96D0C71EF79E81BFD6
// 1000018464
// 00000A8BAD2C26E5A57CDE338C11BF2DD5CA86A49403F4048730418B086ED499
// 1000002168
// Malicious Obj latest coin : 100023
//  Block Chain :
// Block Chain Malicious
// Block Summary: 21D46D6743CB76E51CCC3F97D45FB36439D22097EA6C8D830197535D3D9B62AC
//  Nonce 1000002168
//  dgst 00000A8BAD2C26E5A57CDE338C11BF2DD5CA86A49403F4048730418B086ED499

// Block Summary: 7B403A3BBBF3F4536C63339959FA911273DBD31C7A343B2F3BBF859048E4EA59
//  Nonce 1000061112
//  dgst 00005011ED18F79A2B1AF14F8D8A7A040E3473FBD4D273F0665081F00BE8806D

// Block Summary: E5373C82553982C3F9E7AF7D139AC4742D50EBA23B81A0F681A03E06B7C66D89
//  Nonce 1000014071
//  dgst 0000E18E7E085886C01F04F0DDCCFDB635C2E4C73A8BD016175CEE34ED35A07F

// memberlist
// Member Name : 201
//  Member Coins
// 100000 100006 100012 100018
// Member Name : 202
//  Member Coins
// 100001 100007 100013 100019
// Member Name : 203
//  Member Coins
// 100002 100008 100014 100020
// Member Name : 204
//  Member Coins
// 100003 100009 100015 100021
// Member Name : 205
//  Member Coins
// 100004 100010 100016 100022
// Member Name : 206
//  Member Coins
// 100005 100011 100017 100023

// Lists: 
// F79DE6D621BC1DD16AE5635469FEC87F377A0FE3DF0F8C259DA9A62BE4C2361037BF8AA313822D1F6F56412378C0EC20133D0D1895C7AE54F3DD25C6D5BD28ED9BB072647D72FB5B3D16FB4F5C7386C7C1E5F28534ADF8FF623D17BA3278334E6B91E6CD1FEEB4D53DC5D67034E66F0F0DE2AE0CE21E78BB14DF96D9DD03DAE6B5A526FEDAAB31DC2AAAD5C38FDF644476E84745F2C73A0CF6B0B396201CDDA0B6EF448558548B90ACFCFC090A0ADC3B53F0C0B04FC2B6DA4BE78E771EE5DF99662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0null000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6null0000BF3C377089B03F553F460AA04F350185A65047FF13EE20C8CB211ECA43B8000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6#662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0#1000017575
// Lists: 
// F79DE6D621BC1DD16AE5635469FEC87F377A0FE3DF0F8C259DA9A62BE4C2361037BF8AA313822D1F6F56412378C0EC20133D0D1895C7AE54F3DD25C6D5BD28ED9BB072647D72FB5B3D16FB4F5C7386C7C1E5F28534ADF8FF623D17BA3278334E6B91E6CD1FEEB4D53DC5D67034E66F0F0DE2AE0CE21E78BB14DF96D9DD03DAE6B5A526FEDAAB31DC2AAAD5C38FDF644476E84745F2C73A0CF6B0B396201CDDA0B6EF448558548B90ACFCFC090A0ADC3B53F0C0B04FC2B6DA4BE78E771EE5DF99662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0null000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6null0000BF3C377089B03F553F460AA04F350185A65047FF13EE20C8CB211ECA43B8000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6#662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0#1000017575
// Lists: 
// 8DA70D4F70E933A65209C2A6628D2D04DB1D3B8EDFC9E26A208D0DBEFC6BF995068CB98138488A4C0D35230632DC5D1FE4F763DD152410108CE68D1B3975678B9BB072647D72FB5B3D16FB4F5C7386C7C1E5F28534ADF8FF623D17BA3278334E6B91E6CD1FEEB4D53DC5D67034E66F0F0DE2AE0CE21E78BB14DF96D9DD03DAE6B5A526FEDAAB31DC2AAAD5C38FDF644476E84745F2C73A0CF6B0B396201CDDA0B6EF448558548B90ACFCFC090A0ADC3B53F0C0B04FC2B6DA4BE78E771EE5DF99662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0null000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6null0000BF3C377089B03F553F460AA04F350185A65047FF13EE20C8CB211ECA43B8000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6#662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0#1000017575
// Lists: 
// 8DA70D4F70E933A65209C2A6628D2D04DB1D3B8EDFC9E26A208D0DBEFC6BF995068CB98138488A4C0D35230632DC5D1FE4F763DD152410108CE68D1B3975678B9BB072647D72FB5B3D16FB4F5C7386C7C1E5F28534ADF8FF623D17BA3278334E6B91E6CD1FEEB4D53DC5D67034E66F0F0DE2AE0CE21E78BB14DF96D9DD03DAE6B5A526FEDAAB31DC2AAAD5C38FDF644476E84745F2C73A0CF6B0B396201CDDA0B6EF448558548B90ACFCFC090A0ADC3B53F0C0B04FC2B6DA4BE78E771EE5DF99662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0null000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6null0000BF3C377089B03F553F460AA04F350185A65047FF13EE20C8CB211ECA43B8000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6#662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0#1000017575
// Lists:
// 0700D068719C3AF28C5135995EF694E05D40607EB909AD1BF0A5DBE418FC2B9B930BC6A3F46D66A1BB1F66A893C6153971DF2327742BC563AECDE9DB72D400DA89FF7E113D1AFEDBAE7F798E162D1B73DAA8E90D35C668F90F1D2F8B3E1944E2646A61CED0AC72EC07135270B7B7E4BE21B624548961F278D27DBF0FEB93A18EB5A526FEDAAB31DC2AAAD5C38FDF644476E84745F2C73A0CF6B0B396201CDDA0B6EF448558548B90ACFCFC090A0ADC3B53F0C0B04FC2B6DA4BE78E771EE5DF99662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0null000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6null0000BF3C377089B03F553F460AA04F350185A65047FF13EE20C8CB211ECA43B8000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6#662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0#1000017575
// Lists:
// 0700D068719C3AF28C5135995EF694E05D40607EB909AD1BF0A5DBE418FC2B9B930BC6A3F46D66A1BB1F66A893C6153971DF2327742BC563AECDE9DB72D400DA89FF7E113D1AFEDBAE7F798E162D1B73DAA8E90D35C668F90F1D2F8B3E1944E2646A61CED0AC72EC07135270B7B7E4BE21B624548961F278D27DBF0FEB93A18EB5A526FEDAAB31DC2AAAD5C38FDF644476E84745F2C73A0CF6B0B396201CDDA0B6EF448558548B90ACFCFC090A0ADC3B53F0C0B04FC2B6DA4BE78E771EE5DF99662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0null000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6null0000BF3C377089B03F553F460AA04F350185A65047FF13EE20C8CB211ECA43B8000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6#662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0#1000017575
// Lists:
// BBF31D32A2F8F4A2521A3A64DDF928A215A550325E615F0180B374711D8AF761411DE5C80F45406825C2518398E3BCBF8619424938A84BD99274EF10339A0FE989FF7E113D1AFEDBAE7F798E162D1B73DAA8E90D35C668F90F1D2F8B3E1944E2646A61CED0AC72EC07135270B7B7E4BE21B624548961F278D27DBF0FEB93A18EB5A526FEDAAB31DC2AAAD5C38FDF644476E84745F2C73A0CF6B0B396201CDDA0B6EF448558548B90ACFCFC090A0ADC3B53F0C0B04FC2B6DA4BE78E771EE5DF99662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0null000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6null0000BF3C377089B03F553F460AA04F350185A65047FF13EE20C8CB211ECA43B8000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6#662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0#1000017575
// Honest Obj latest coin : 100032
//  Block Chain :
// Block Chain Honest
//  Block Summary: 662AC1F1AB58C4A5BCF530BAFB63F7B9A4AD9A9A233980934A8DAE9EF3DCCBE0
//  Nonce 1000017575
//  dgst 0000BF3C377089B03F553F460AA04F350185A65047FF13EE20C8CB211ECA43B8

// Block Summary: BF53491A73E0B79A6D08312A33B7B5BA8437EAE5AD3BE6A6791046AC6A6B4152
//  Nonce 1000030337
//  dgst 000093F6917F7BC0B3EA6A7DE310860CA89BA5739998E7AAFD5B9DBF0222A2B6

// Block Summary: 8AAFD5B47EA0A02264734D7C35B449AC4193DC21DB27FD3BDC13EB07FAB63D17
//  Nonce 1000031111
//  dgst 000044AE187B3D56CB1A634B97345D16A9A94BEBA0857C5F40E560F0A641ACC7

// Block Summary: B81A119A89DBB91D3F6A7F7218B79CC65065AE990ACA7757AEB2A23CF0C46CD4
//  Nonce 1000036536
//  dgst 0000ABC9F116EE4E7EE9AFC0E47F52FAD13FC5E7092D7A1498ACEC93C84C6222

// Block Summary: F06F11F69E6BABD3671DBC89C325B301A310A64B2A73853FCEE758B77EF5A6EA
//  Nonce 1000068652
//  dgst 0000EC877F4101B5EE13C400C78174112C5DE09C4C0B7492F9554CAE3B52D7CD

// memberlist
// Member Name : Member 0
//  Member Coins
// 100003 100012 100016 100020 100024 100028 100032
// Member Name : Member 1
//  Member Coins
// 100000 100004 100013 100017 100021 100025 100029
// Member Name : Member 2
//  Member Coins
// 100001 100005 100010 100014 100018 100022 100026 100030
// Member Name : Member 3
//  Member Coins
// 100002 100006 100011 100015 100019 100023 100027 100031