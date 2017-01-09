package databasemanage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;


/**
 * Created by tangjie on 2017/1/3.
 */
public class DBOperation {
    private static Logger logger = Logger.getLogger(DBOperation.class);//ÈÕÖ¾²¿·Ö

    /**
     * Àà·½·¨£¬Ö´ÐÐÓÐ½á¹û¼¯·µ»ØµÄsql²Ù×÷
     * @param sql SQLÓï¾ä
     * @return ½á¹û¼¯
     * @throws SQLException
     */
    public static ResultSet execSQL(String sql) throws SQLException
    {
        DBConnection DBC=DBConnection.getInstance();
        Connection conn= DBC.getConnection();
        Statement stmt=conn.createStatement();
        System.out.println(sql);
        ResultSet rs=stmt.executeQuery(sql);
        DBC.close(conn);//¹é»¹Á¬½Ó³Ø
        return rs;
    }
    /**
     * Ö´ÐÐÎÞ½á¹û¼¯·µ»ØµÄSQLÓï¾ä
     * @param dm SQLÓï¾ä
     * @return Ö´ÐÐ³É¹¦·µ»Øtrue£¬·ñÔò·µ»Øfalse
     * @throws SQLException
     */
    public static boolean exec(String dm)
    {
        DBConnection DBC=null;
        Connection conn=null;
        try{
            DBC=DBConnection.getInstance();
            conn= DBC.getConnection();
            //DriverManager.setLoginTimeout(10); //ÔOÖÃßB½Ó³¬•r
            PreparedStatement ps= (PreparedStatement) conn.prepareStatement(dm);
            if(!ps.execute())
            {
                DBC.close(conn);//¹é»¹Á¬½Ó³Ø
                System.out.println("write database success!");
                return true;
            }
            else
            {
                DBC.close(conn);//¹é»¹Á¬½Ó³Ø
                logger.info("Êý¾Ý¿â²Ù×÷Ò»´Î¡ª¡ªÑÏÖØÒì³£"+dm);
                //new DBOperation().inserterrorSQL(dm);//½«´íÎóSQLÓï¾ä²åÈëÊý¾Ý¿â
                System.out.println("write database error!");
                return false;
            }
        }catch(Exception ee){
            DBC.close(conn);//¹é»¹Á¬½Ó³Ø
            logger.error("Êý¾Ý¿â²Ù×÷Ò»´Î¡ª¡ªÑÏÖØÒì³£"+dm);
            //new DBOperation().inserterrorSQL(dm);//½«´íÎóSQLÓï¾ä²åÈëÊý¾Ý¿â
            System.out.println("write database error!");
            return false;
        }
    }
    /**
     * ½«Êý¾ÝÄ£ÐÍ¶ÔÏóÐ´µ½Êý¾Ý¿â
     * @param dm Êý¾ÝÄ£ÐÍ¶ÔÏó
     * @return Ö´ÐÐ³É¹¦·µ»Øtrue£¬·ñÔò·µ»Øfalse
     * @throws SQLException
     */
    public static boolean insert(String dm) throws SQLException
    {
        return DBOperation.exec(dm);
    }

    /*
     * ²åÈëÒ½Éú¼ÇÂ¼
     * */
    public static boolean insertDoctorInfo(String DoctorName,String DoctorPaperId,String DoctorConId,String doctorPatientId , String SatInfo,
                                           String AllVisitCount,String YesterdayVisitCount,String AllPaper,String AllPatient,String YesterdayPatientCount,
                                           String AfterPatientCount,String PatientVote,String ThxMailCount,String giftCount,String lastTime,String CreateTime)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            //System.out.println(df.format(new Date()));// new Date()Îª»ñÈ¡µ±Ç°ÏµÍ³Ê±¼ä
            sql="insert into doctorinformation(DoctorName,SatInfo,DoctorPaperId,DoctorConId,doctorPatientId,"
                    + "AllVisitCount,YesterdayVisitCount,AllPaper,AllPatient,YesterdayPatientCount,"
                    + "AfterPatientCount,PatientVote,ThxMailCount,giftCount,lastTime,CreateTime,"
                    + "UpdateTime) values('"+DoctorName+"','"+SatInfo+"','"+DoctorPaperId+"','"+DoctorConId+"','"+doctorPatientId+"','"
                    +AllVisitCount+"','"+YesterdayVisitCount+"','"+AllPaper+"','"+AllPatient+"','"+YesterdayPatientCount+"','"+AfterPatientCount+"','"+PatientVote+"','"
                    +ThxMailCount+"','"+giftCount+"','"+lastTime+"','"+CreateTime+"','"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("Ò½Éú¼ÇÂ¼²åÈëÊý¾Ý¿â´íÎó¡ª¡ªÑÏÖØÒì³£"+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * ²åÈëÎÄÕÂ
     * */
    public static boolean insertDoctorPaper(String doctorId,String TitleType,String TitleName,String TitleBody,int clickCount,String TitleTime)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctorpaperinformation(doctorId,TitleType,TitleName,TitleBody,clickCount,TitleTime,UpdateTime) values('"+doctorId+"','"+
                    TitleType+"','"+TitleName+"','"+TitleBody+"',"+clickCount+",'"+TitleTime+"','"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("Ò½Éú¼ÇÂ¼ÎÄÕÂµ½Êý¾Ý¿â´íÎó¡ª¡ªÑÏÖØÒì³£"+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * ²åÈë×ÉÑ¯ÇøÁÐ±í
     * */
    public static boolean insertConsulationList(String DoctorConId,String PatientName,String IDDTitle,String Title,String Relation,String DiaCount,String href)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctorconsulationlist(DoctorConId,PatientName,IDDTitle,Title,Relation,DiaCount,href,EntTime) values('"+DoctorConId+"','"+PatientName+"','"+
                    IDDTitle+"','"+Title+"','"+Relation+"','"+DiaCount+"','"+href+"','"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("Ò½Éú¼ÇÂ¼×ÉÑ¯ÇøÁÐ±íµ½Êý¾Ý¿â´íÎó¡ª¡ªÑÏÖØÒì³£"+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * ²åÈëÒ½ÉúÔºÊý¾Ý¿âÁÐ±í
     * */
    public static boolean insertdoctorSource(String DoctorName,String Department,String Hospial,String Doctorlisturl,String DoctorUrl)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctoralllist(DoctorName,Hospital,Department,Doctorlisturl,DoctorUrl,Isprocess,CreatTime) values('"+DoctorName+"','" + Hospial + "','"+Department+"','"+
                    Doctorlisturl+"','"+DoctorUrl+"',"+false+",'"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("Ò½ÉúÁÐ±íÔ´Êý¾Ýµ½Êý¾Ý¿â´íÎó¡ª¡ªÑÏÖØÒì³£"+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }


    /*
     * ²åÈë×ÉÑ¯ÇøÂÛÌ³ÄÚÈÝ£¬Ò½Éú»Ø¸´ºÍ»¼ÕßÎÊ´ð
     * */
    public static boolean insertConsulationBBSContent(String doctorID,String IDDTitle,String Type,String Status,String Content,String Time)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into consulationbbsinformation(doctorID,IDDTitle,Type,Status,Content,Time,UpdateTime) values('"+doctorID+"','"+
                    IDDTitle+"','"+Type+"','"+Status+"','"+Content+"','"+ Time +"','"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("Ò½Éú¼ÇÂ¼²åÈë×ÉÑ¯ÇøÂÛÌ³ÄÚÈÝ£¬Ò½Éú»Ø¸´ºÍ»¼ÕßÎÊ´ðµ½Êý¾Ý¿â´íÎó¡ª¡ªÑÏÖØÒì³£"+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * Ð´Èë »¼ÓÑÇø ÁÐ±íÊý¾Ý
     * */
    public static boolean insertPatientFriend(String doctorPatientId,String TitleIDD,String Title,String Url,String Author,String ReceiveCount,String EndTime)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into patientfriendlist(doctorPatientId,TitleIDD,Title,Url,Author,ReceiveCount,EndTime,UpdateTime) values('"+doctorPatientId+"','"+
                    TitleIDD+"','"+Title+"','" + Url + "','"+Author+"','"+ReceiveCount+"','"+EndTime+"','"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("»¼ÓÑÇøÁÐ±íÊý¾Ýµ½Êý¾Ý¿â´íÎó¡ª¡ªÑÏÖØÒì³£"+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * Ð´Èë »¼ÓÑÇø BBS Êý¾Ý
     * */
    public static boolean insertPatientFriendBBS(String TitleIDD,String Name,String UserIntegral,String EnquiryPoint,String Content,String EndTime)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into patientfriendbbs(TitleIDD,Name,UserIntegral,EnquiryPoint,Content,EndTime,UpdateTime) values('"+TitleIDD+"','"+
                    Name+"','"+UserIntegral+"','" + EnquiryPoint + "','"+Content+"','"+EndTime+"','"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("»¼ÓÑÇøBBSÊý¾Ýµ½Êý¾Ý¿â´íÎó¡ª¡ªÑÏÖØÒì³£"+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * Ð´Èë Ò½ÉúÐÅÏ¢ Êý¾Ý
     * */
    public static boolean insertthankstable(String url,String thanksletter,String comments)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into thankslettertable(url,thanksletter,comments,UpdateTime) values('"+url+"','"+
                    thanksletter+"','"+comments+"','"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("»¼ÓÑÇøBBSÊý¾Ýµ½Êý¾Ý¿â´íÎó¡ª¡ªÑÏÖØÒì³£"+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }


    /*
     * ¸ù¾ÝURL¸úÐÂ»¼Õß·þÎñÐÇ
     * */
    public static boolean updatesrevicestar(String DoctorUrl,Integer  servicestar)
    {
        String sql ="";
        try{
            sql = "update thankslettertable set servicestar =" + servicestar + " where doctorUrl = '" + DoctorUrl + "'";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("Ô´Êý¾ÝÖ´ÐÐ´ËÒ½ÉúÊÇ·ñÍê³É²Ù×÷¡ª¡ªÑÏÖØÒì³£"+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * ¸ù¾ÝURL¶ÔÄ³Ò½ÉúÅÐ¶¨ÊÇ·ñÖ´ÐÐÊý¾Ý×¥È¡Íê±Ï
     * */
    public static boolean isFinished(String DoctorUrl,boolean enable)
    {
        String sql ="";
        try{
            sql = "update doctoralllist set Isprocess =" + enable + " where DoctorUrl = '" + DoctorUrl + "'";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("Ô´Êý¾ÝÖ´ÐÐ´ËÒ½ÉúÊÇ·ñÍê³É²Ù×÷¡ª¡ªÑÏÖØÒì³£"+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * ¶ÁÈ¡Ò½ÉúÔ´Êý¾ÝÁÐ±í
     * */
    public static ResultSet getDoctorList()
    {
        try{
            String sql = "select DoctorName,DoctorUrl from doctoralllist where Isprocess = false and DoctorUrl !=''";
            System.out.println(sql);
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("Ô´Êý¾ÝÒ½ÉúÊý¾Ý¶ÁÈ¡³É²Ù×÷¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return null;
        }
    }

    /*
     * ¶ÁÈ¡Ò½ÉúurlÁÐ±í
     * */
    public static ResultSet getdoctorinfo()
    {
        try{
            String sql = "select distinct doctorUrl from doctoralllist";
            System.out.println(sql);
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("Ô´Êý¾ÝÒ½ÉúÊý¾Ý¶ÁÈ¡³É²Ù×÷¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return null;
        }
    }

    /*
     * Ò½ÉúÐÅÏ¢Ð´ÈëÊý¾Ý¿â
     * Õâ¸öDB²Ù×÷·½·¨ÊÇ³ÌÐò2¶ÀÓÐ
     * **/
    public static boolean doctorInfoWritetoDB(String doctorMD5,String doctorName,String hospitalPlace,String hospitalName,String hospitalGrade,
                                              String hospitalKs,String hospitalProfessor,String schoolProfessor,
                                              String thanksLetter,String giftCount,String patientVotes,
                                              String satisfactionEffect,String satisfactionService,
                                              String back2Weeks,String freeConsult,boolean payOrnot,
                                              String pay,String payTime,String payConsultCount,boolean isMzTime,boolean isMzYy,boolean doctorPhoto,
                                              boolean doctorLinkOrnot,String doctorLinkAddress,
                                              String doctorPaperCount,String contributionValue,
                                              String doctorHomeVisitCount,String yesterdayVisitCount,
                                              String webSiteCreateTime,String outputFlag)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
        try{
            String sql = "insert into doctorbriefinformation(doctorMD5,doctorName,hospitalPlace,hospitalName,hospitalGrade,hospitalKs,"
                    + "hospitalProfessor,schoolProfessor,thanksLetter,giftCount,patientVotes,"
                    + "satisfact,satisfactionService,back2Weeks,freeConsult,payOrnot,"
                    + "pay,payTime,payConsultCount,isMzTime,isMzYy,doctorPhoto,doctorLinkOrnot,doctorLinkAddress,doctorPaperCount,"
                    + "contributionValue,doctorHomeVisitCount,yesterdayVisitCount,"
                    + "webSiteCreateTime,dataUpdateTime,outputFlag) values('" + doctorMD5 + "','" + doctorName +"','" + hospitalPlace +"','" + hospitalName + "','" + hospitalGrade + "','" + hospitalKs +
                    "','"+ hospitalProfessor +"','" + schoolProfessor + "','" + thanksLetter +
                    "','" + giftCount +"','" + patientVotes +"','" + satisfactionEffect +"','" + satisfactionService +
                    "','" + back2Weeks + "','" + freeConsult +"'," + payOrnot + ",'" + pay +"','" + payTime + "','" + payConsultCount + "'," + isMzTime + "," + isMzYy +
                    "," + doctorPhoto + "," + doctorLinkOrnot + ",'" + doctorLinkAddress + "','" + doctorPaperCount + "','" + contributionValue + "','" + doctorHomeVisitCount + "','" + yesterdayVisitCount +
                    "','" + webSiteCreateTime + "','" + df.format(new Date()) + "','" + outputFlag + "')";
            sql = sql.replace("'null'", "null");
            System.out.println("sql:" + sql);
            return DBOperation.exec(sql);//Ö´ÐÐSQLÓïÑÔ
        }catch(Exception ee){
            logger.error("Ò½Éú¼òÂÔÐÅÏ¢Ð´ÈëÊý¾Ý¿â¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }


    /*
     * ¶ÁÈ¡Ä³¸ö±êºÅµÄÊý¾Ý
     * **/
    public static ResultSet readDoctorbriefData(String outputFlag)
    {
        try{
            String sql = "select * from doctorbriefinformation where outputFlag = '" + outputFlag + "'";
            //System.out.println(sql);
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("¶ÁÈ¡Ä³¸ö±êºÅÊý¾Ý²Ù×÷¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return null;
        }
    }

    /*
     * Í¶Æ±Êý£¬ÏêÏ¸Çé¿ö²åÈë
     * */
    public static boolean insertDetailVote(String doctorMD5,String bName,String bVote)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "insert into b_detail_vote(doctorid,bName,bVote,creattime) values('" + doctorMD5 + "','" + bName +"','" + bVote +"','" + df.format(new Date()) + "')";
            System.out.println("sql:" + sql);
            return DBOperation.exec(sql);//Ö´ÐÐSQLÓïÑÔ
        }catch(Exception ee){
            logger.error("Í¶Æ±Êý£¬ÏêÏ¸Çé¿ö²åÈë¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * Í¶Æ±Êý£¬¸üÐÂ²åÈë
     *
     * */
    public static boolean updateDetailVote(String doctorMD5,String bName,String bVote)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "update b_detail_vote set bVote = '"+bVote+"',creattime ='"+df.format(new Date())+"' where doctorid ='"+doctorMD5+"' and bName='"+bName+"'";
            System.out.println("sql:" + sql);
            return DBOperation.exec(sql);//Ö´ÐÐSQLÓïÑÔ
        }catch(Exception ee){
            logger.error("Í¶Æ±Êý£¬ÏêÏ¸Çé¿ö¸üÐÂ¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }


    /*
     * ¼ì²é´ËÒ½ÉúÍ¶Æ±ÊÇ·ñ´æÔÚ
     *
     * */
    public static ResultSet checkDetailVote(String doctorMD5,String bName)
    {
        try{
            String sql = "select * from b_detail_vote where doctorid='"+doctorMD5+"' and bName='"+bName+"'";
            System.out.println("sql:" + sql);
            return DBOperation.execSQL(sql);//Ö´ÐÐSQLÓïÑÔ
        }catch(Exception ee){
            logger.error("Í¶Æ±Êý£¬ÏêÏ¸Çé¿ö¼ì²éÊÇ·ñ´æÔÚ¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return null;
        }
    }


    /*
     * Ò½Éú¾ßÌåÍ¶Æ±Êý£¬ÔÚ³ÌÐò¿ªÊ¼ÔËÐÐÖ®Ç°Çå¿Õ
     * */
    public static boolean deleteallVoteCount()
    {
        try{
            String sql = "truncate table b_detail_vote";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            return false;
        }
    }

    /*
     * ¸ÐÐ»ÐÅÊý¾Ý²åÈëÊý¾Ý¿â
     *
     * */
    public static boolean insertdoctorthanksletter(String doctorid,String patientname,String lotcation,String disease,String effect,String attitude,String content,String thistimeFlag)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "insert into doctorthanksletter(doctorid,patientname,lotcation,disease,effect,attitude,content,creatTime,thistimeFlag) values('" + doctorid + "','" + patientname +"','" + lotcation +"','"+disease+"','"+effect+"','"+ attitude+"','"+content+"','"+ df.format(new Date())+"','"+thistimeFlag + "')";
            System.out.println("sql:" + sql);
            return DBOperation.exec(sql);//Ö´ÐÐSQLÓïÑÔ
        }catch(Exception ee){
            logger.error("¸ÐÐ»ÐÅÊý¾Ý²åÈëÊý¾Ý¿â"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * ×îÐÂ¶©µ¥Êý¾Ý²åÈëÊý¾Ý¿â
     *
     * */
    public static boolean insertdoctordingd(String doctorid,String patientname,String lotcation,String dingdanin,String effect,String content,String thistimeFlag)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "insert into doctornewdingd(doctorid,patientname,lotcation,dingdanin,effect,content,creatTime,thistimeFlag) values('" + doctorid + "','" + patientname +"','" + lotcation +"','"+dingdanin+"','"+effect+"','"+ content+"','"+ df.format(new Date())+"','"+thistimeFlag + "')";
            System.out.println("sql:" + sql);
            return DBOperation.exec(sql);//Ö´ÐÐSQLÓïÑÔ
        }catch(Exception ee){
            logger.error("×îÐÂ¶©µ¥Êý¾Ý²åÈëÊý¾Ý¿â"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * ÓÃÓÚ¼ÇÂ¼´íÎóµÄ¼ÇÂ¼µÄurl===>>>²åÈëÊý¾Ý¿â
     *
     * */
    public static boolean inserterrorurl(String title,String url,String doctorid,String doctorname,String doctorPatientId,String IDDTitle,String thistimeFlag,int typeid)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "insert into errorurllist(title,url,doctorid,doctorname,doctorPatientId,IDDTitle,thistimeFlag,createTime,typeid,iserror,updateTime) values('" + title + "','" + url +"','" + doctorid + "','"+doctorname+"','" + doctorPatientId +
                    "','" +IDDTitle + "','"+ thistimeFlag + "','"+ df.format(new Date()) +"',"+typeid+",1,'"+ df.format(new Date()) + "')";
            System.out.println("sql:" + sql);
            return DBOperation.exec(sql);//Ö´ÐÐSQLÓïÑÔ
        }catch(Exception ee){
            logger.error("ÓÃÓÚ¼ÇÂ¼´íÎóµÄ¼ÇÂ¼µÄurl===>>>²åÈëÊý¾Ý¿â"+"----ee>>>"+ee.toString());
            return false;
        }
    }


    /*
     * ¶ÁÈ¡´íÎóÔ´Êý¾ÝÁÐ±í
     * */
    public static ResultSet geterrorList()
    {
        try{
            String sql = "select * from errorurllist where iserror = 1";
            System.out.println(sql);
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("´íÎóÁÐ±íÊý¾Ý¶ÁÈ¡³É²Ù×÷¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return null;
        }
    }

    /*
     * ¸ù¾ÝID½«´íÎóÔ´ÁÐ±íÖÐÄ³¼ÇÂ¼ÖÃÎ»
     * */
    public static boolean useIdseterrorList(int id)
    {
        try{
            String sql = "update errorurllist set iserror=0 where id ="+id;
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´íÎóÁÐ±íÊý¾ÝÖÃÎ»²Ù×÷¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*
     * ½«´íÎóSQLÐ´Èëµ½errorSQL±íÖÐ
     * */
    public static boolean inserterrorSQL(String errorSQL)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "insert into errorsql(errorSQL,ischeck,createtime) values('"+errorSQL+"',0,'"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("½«´íÎóSQLÓï¾ä²åÈëÊý¾Ý¿â¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }



    /*
     * ÐÂ³ÌÐò-½«Ò½Éúurl²åÈëµ½Êý¾Ý¿â
     * diseasename-±íÊ¾´Ë¼ÇÂ¼ÊÇÊôÓÚºÎÖÖ²¡
     *
     * **/
    public static boolean insertNewDinDan(String url,String diseasename)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "insert into newgetdindan(doctorbriefurl,diseasename,iscomplete,createtime,completetime) values('"+url+"','"+diseasename+"',0,'"+df.format(new Date())+"','"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("³ÌÐòÈý½«´íÎóSQLÓï¾ä²åÈëÊý¾Ý¿â¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /**
     * ÐÂ³ÌÐò-½«Ò½Éúurl²åÈëµ½Êý¾Ý¿â==ÏÈ¼ì²éÒ½ÉúÊÇ·ñ´æÔÚ£¬²»ÖØ¸´²åÈë
     *
     * */
    public static boolean isHaveInNewDinDan(String url,String diseasename)
    {
        try{
            String sql = "select * from newgetdindan where doctorbriefurl ='" + url + "' and diseasename='"+diseasename+"'";
            ResultSet ds =DBOperation.execSQL(sql);
            ds.last();//ÒÆ¶¯µ½×îºó
            if(ds.getRow()>=1)
                return true;
            return false;
        }catch(Exception ee){
            logger.error("³ÌÐòÈý²éÑ¯Ò½Éú¼òÒªÐÅÏ¢URL¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /**
     * ÐÂ³ÌÐò-½«Ò½Éúurl²åÈëµ½Êý¾Ý¿â==½«Íê³ÉÎ»È«²¿Îª1»òÕßÎª0
     *
     * */
    public static boolean setAndGetNewDinDan(int flag)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "update newgetdindan set iscomplete =" + flag + ", completetime ='" + df.format(new Date()) + "'";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("³ÌÐòÈý¸´Î»ºÍÖÃÎ»Ò½Éú¼òÒªÐÅÏ¢URL¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /**
     * ÐÂ³ÌÐò-½«Ò½Éúurl²åÈëµ½Êý¾Ý¿â==¸ù¾ÝURLÉèÖÃÍê³ÉÎ»
     *
     * */
    public static boolean setNewDinDan(String url)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "update newgetdindan set iscomplete =1, completetime ='" + df.format(new Date()) + "' where doctorbriefurl='" + url + "'";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("³ÌÐòÈýÖÃÎ»Ò½Éú¼òÒªÐÅÏ¢URL¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /**
     * ÐÂ³ÌÐò-½«Ò½Éúurl²åÈëµ½Êý¾Ý¿â==¶ÁÈ¡ÁÐ±íÊý¾Ý
     *
     * */
    public static ResultSet GetNewDinDan(String diseasename)
    {
        try{
            String sql = "select * from newgetdindan where iscomplete ='0' and diseasename ='"+diseasename+"'";
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("³ÌÐòÈý¶ÁÈ¡Ò½Éú¼òÒªÐÅÏ¢list¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return null;
        }
    }

    /*
     * ÐÂ³ÌÐò-½«Ò½Éú×îÐÂ¶©µ¥Êý¾Ý²åÈëµ½Êý¾Ý¿â
     *
     *
     * **/
    public static boolean insertNewDinDanData(String doctorUrl,String patient,String place,String dingID,String state,String urll,String paDes,String paDetail,String md5Str,String diseasename)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "insert into dindandetailinfor(doctorUrl,diseasename,patient,place,dingID,state,urll,paDes,paDetail,md5Str,createtime) values('"+doctorUrl+"','"+diseasename+"','"+
                    patient+"','"+place+"','"+dingID+"','"+state+"','"+urll+"','"+paDes+"','"+paDetail+"','"+md5Str+"','"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("³ÌÐòÈýÒ½Éú×îÐÂ¶©µ¥Êý¾Ý²åÈëµ½Êý¾Ý¿â¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /**
     * ÐÂ³ÌÐò-½«Ò½Éú×îÐÂ¶©µ¥Êý¾Ý²åÈëµ½Êý¾Ý¿â==ÏÈ¼ì²éÒ½ÉúÊÇ·ñ´æÔÚ£¬²»ÖØ¸´²åÈë
     *
     * */
    public static boolean isHaveInNewDinDanData(String md5Str)
    {
        try{
            String sql = "select * from dindandetailinfor where md5Str ='" + md5Str + "'";
            ResultSet ds =DBOperation.execSQL(sql);
            ds.last();//ÒÆ¶¯µ½×îºó
            if(ds.getRow()>=1)
                return true;
            return false;
        }catch(Exception ee){
            logger.error("³ÌÐòÈý²éÑ¯Ò½Éú×îÐÂ¶©µ¥Êý¾ÝÐÅÏ¢URL¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /**
     * ÐÂ³ÌÐò-´íÎóµØÖ·ÖØÖ´ÐÐ-²åÈë
     *
     * */
    public static boolean inserterrorNewDinDanUrl(String doctorUrl,String diseasename)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "insert into errordindanurl(errordindandoctorurl,diseasename,isdelete,createtime,deletetime) values('"+doctorUrl+"','"+diseasename+"',0,'"+
                    df.format(new Date())+"','"+df.format(new Date())+"')";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("³ÌÐòÈýÒ½Éú×îÐÂ¶©µ¥×¥È¡URL´íÎó²åÈëµ½Êý¾Ý¿â£¨Îªºó¼ÌÖ´ÐÐÖØÐÂ×¥È¡£©¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /**
     * ÐÂ³ÌÐò-´íÎóµØÖ·ÖØÖ´ÐÐ-Âß¼­É¾³ý
     *
     * */
    public static boolean updateerrorNewDinDanUrl(String doctorUrl)
    {
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            String sql = "update errordindanurl set isdelete=1,deletetime='"+df.format(new Date())+"' where errordindandoctorurl='"+doctorUrl+"'";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("³ÌÐòÈýÒ½Éú×îÐÂ¶©µ¥×¥È¡URL´íÎóÂß¼­É¾³ýµ½Êý¾Ý¿â£¨Îªºó¼ÌÖ´ÐÐÖØÐÂ×¥È¡£©¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /**
     * ÐÂ³ÌÐò-½«Ò½Éú´íÎóµØÖ·²åÈëµ½Êý¾Ý¿â==¶ÁÈ¡ÁÐ±íÊý¾Ý
     *
     * */
    public static ResultSet GeterrorNewDinDan()
    {
        try{
            String sql = "select * from errordindanurl where isdelete ='0'";
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("³ÌÐòÈý¶ÁÈ¡Ò½Éú´íÎóurlµ½list¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return null;
        }
    }

    /**
     * ¶ÁÈ¡Êý¾Ý±íÖÐ°´ÕÕ£¨Äê-ÔÂ£©µÄÊ±¼ä
     *
     * */
    public static ResultSet getallyearanddate()
    {
        try{
            //String sql = "select distinct date_format(Time,'%Y-%m') from consulationbbsinformation";
            //String sql = "select distinct date_format(createtime,'%Y-%m') from dindandetailinfor";
            //String sql = "select distinct date_format(dataUpdateTime,'%Y-%m') from doctorbriefinformation";
            //String sql = "select distinct date_format(EntTime,'%Y-%m') from doctorconsulationlist";
            String sql = "select distinct date_format(UpdateTime,'%Y-%m') from doctorpaperinformation";
            //String sql = "select distinct date_format(creatTime,'%Y-%m') from doctorthanksletter";
            //String sql = "select distinct date_format(EndTime,'%Y-%m') from patientfriendbbs";
            //String sql = "select distinct date_format(EndTime,'%Y-%m') from patientfriendlist";
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("µ¼³ö±íÊý¾Ýµ½ExcelÄêÔÂÊ±¼ä¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return null;
        }
    }

    /**
     * °´ÕÕÉÏÃæµÄÄêÔÂ½øÐÐÊý¾Ý³éÈ¡
     * */
    public static ResultSet useYeardatatoExcel(String yeardate)
    {
        try{
            String sql = "select * from doctorconsulationlist where date_format(EntTime,'%Y-%m')='"+yeardate+"'";
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("µ¼³ö±íÊý¾Ýµ½ExcelÄêÔÂÊ±¼ä(¸ù¾Ý¾ßÌåÊ±¼ä³éÈ¡Êý¾ÝÒì³£)¡ª¡ªÑÏÖØÒì³£"+"----ee>>>"+ee.toString());
            return null;
        }
    }



    /******************************************************************************************************************/
    //´óµÚ¶þ°æ³ÌÐò
    /******************************************************************************************************************/
    //Ò½ÉúÔ´µØÖ·
    //½¨Á¢md5strË÷Òý£¬ÓÐÔò¸üÐÂ£¬·ñÔò²åÈë
    //»ùÓÚMD5Ë÷Òý
    public static boolean insertandupdatedoctorresource(String doctorName,String type,String doctorHospital,String doctorProfession,String doctorUrl,String docotorHomepage,String recommended,String recomcontent,String md5str)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctor_info_resource(doctorName,type,doctorHospital,doctorProfession,doctorUrl,docotorHomepage,recommended,recomcontent,md5str,updatetime) values('"+doctorName+"','"+type+"','"+doctorHospital+"','"
                    +doctorProfession+"','"+doctorUrl+"','"+docotorHomepage+"','"+recommended+"','"+recomcontent+"','"+md5str+"','"+df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈëÒ½ÉúÔ­µØÖ·ÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }
    //»¼ÓÑ»á
    //½¨Á¢md5strË÷Òý£¬ÓÐÔò¸üÐÂ£¬·ñÔò²åÈë
    //»ùÓÚMD5Ë÷Òýhytitle+hyhuifu(±êÌâºÍ»Ø¸´´ÎÊý)==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdatehuanyou(String doctorName,String type,String md5str,String hytitle,String hyurl,String groups,String author,String hyhuifu,String hyhuifutime,String hyhuimd5str)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into huanyouhui_table_resource(doctorName,type,md5str,hytitle,hyurl,groups,author,hyhuifu,hyhuifutime,hyhuimd5str,updatetime) values('"+doctorName+"','" + type + "','"+ md5str + "','"+hytitle+"','"+hyurl+"','"+groups+"','"+author+"','"
                    +hyhuifu+"','"+hyhuifutime+"','"+hyhuimd5str+"','"+df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈëÒ½Éú»¼ÓÑ»áÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }
    //Ò½Éú»ù±¾ÐÅÏ¢²¹³ä
    public static boolean insertdoctorextrainfo(String doctorName,String type,String md5str,String schoolprofession,String pvote,String price,String goodsatic,String zhongsatic,String chasatic)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctorextrainfo_table(doctorName,type,md5str,schoolprofession,pvote,price,goodsatic,zhongsatic,chasatic,updatetime) values('"+doctorName+"','" + type + "','"+ md5str + "','"+schoolprofession+"','"+pvote+"','"+price+"','"
                    +goodsatic+"','"+zhongsatic+"','"+chasatic+"','" +df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈëÒ½Éú»¼ÓÑ»áÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }
    public static boolean inserthuanyou(String hytitle,String hyhuimd5str,String users,String content,String uptime,String floor)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into huanyouhui_table(hytitle,hyhuimd5str,users,content,uptime,floor,updatetime) values('"+hytitle+"','" + hyhuimd5str + "','"+ users + "','"+content+"','"+uptime+"','"+floor+"','"
                    +df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈëÒ½Éú»¼ÓÑ»áÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }
    //Ò½ÔºÐÅÏ¢
    public static boolean insertandupdatehospital(String hospital,String hospitalmd5,String shengfen,String district,String rank)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into hospitalinfo_table(hospital,hospitalmd5,shengfen,district,rank,updatetime) values('"+hospital+"','" + hospitalmd5 + "','"+ shengfen + "','"+district+"','"+rank+"','"+df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈëÒ½Éú»¼ÓÑ»áÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }
    //ÎÄÕÂ
    //½¨Á¢md5strË÷Òý£¬ÓÐÔò¸üÐÂ£¬·ñÔò²åÈë
    //»ùÓÚMD5Ë÷Òýpapertitle+num(±êÌâºÍ»Ø¸´´ÎÊý)==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdatepaper(String doctorName,String type,String md5str,String papertitle,String papertype,String paperurl,String peopleread,String submittime)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctorpaper_table_resource(doctorName,type,md5str,papertitle,papertype,paperurl,peopleread,submittime,updatetime) values('"+doctorName+"','"+type+"','"+md5str+"','"+papertitle+"','"+papertype+"','"
                    +paperurl+"','"+peopleread+"','"+submittime+"','"+df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈëÒ½ÉúÎÄÕÂÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //»¼ÓÑ·þÎñÇø
    //½¨Á¢md5strË÷Òý£¬ÓÐÔò¸üÐÂ£¬·ñÔò²åÈë
    //»ùÓÚMD5Ë÷Òýpatienttitle+huifunum(±êÌâºÍ»Ø¸´´ÎÊý)==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdatehuanyouservice(String doctorName,String type,String md5str,String patientname,String patienttitle,String titleurl,String disease,String huifunum,String huifutime,String hzsmd5str)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into huanyouservice_table_resource(doctorName,type,md5str,patientname,patienttitle,titleurl,disease,huifunum,huifutime,hzsmd5str,updatetime) values('"+doctorName+"','"+type+"','"+md5str+"','"+patientname+"','"+patienttitle+"','"
                    +titleurl+"','"+disease+"','"+huifunum+"','"+huifutime+"','"+hzsmd5str+"','"+df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈë»¼ÓÑ·þÎñÇøÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }
    public static boolean inserthuanyouservice(String md5str,String hzsmd5str,String patienttitle,String users,String states,String content,String floor,String uptime,String servicemd5str)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into huanyouservice_table(md5str,hzsmd5str,patienttitle,users,states,content,floor,uptime,servicemd5str,updatetime) values('"+md5str+"','"+hzsmd5str+"','"+patienttitle+"','"+users+"','"+states+"','"
                    +content+"','"+floor+"','"+uptime+"','"+servicemd5str+"','"+df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈë»¼ÓÑ·þÎñÇøÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //»¼ÓÑ¸ÐÐ»ÐÅ
    //½¨Á¢md5strË÷Òý£¬ÓÐÔò¸üÐÂ£¬·ñÔò²åÈë
    //»ùÓÚMD5Ë÷Òýpatient+location+content(²¡ÈËÐÕÃû+µØÖ·+ÄÚÈÝ)==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdatethanksletter(String md5str,String type,String patient,String location,String disease,String uptime,String liaoxiao,String taidu,String recommend,String content)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into thanksletter_table_resource(md5str,type,patient,location,disease,uptime,liaoxiao,taidu,recommend,content,updatetime) values('"+md5str+"','"+type+"','"+patient+"','"+location+"','"
                    +disease+"','" +uptime+"','"+liaoxiao+"','"+taidu+"','"+recommend+"','"+content+"','"+df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈë¸ÐÐ»ÐÅÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }
    //»¼ÓÑ¿´²¡¾­Ñé
    public static boolean insertandupdatejiuyijinyan(String md5str,String type,String patient,String location,String disease,String uptime,String liaoxiao,String taidu,String recommend,String content)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into jiuyijinyan_table_resource(md5str,type,patient,location,disease,uptime,liaoxiao,taidu,recommend,content,updatetime) values('"+md5str+"','"+type+"','"+patient+"','"+location+"','"
                    +disease+"','" +uptime+"','"+liaoxiao+"','"+taidu+"','"+recommend+"','"+content+"','"+df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈë¸ÐÐ»ÐÅÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //»¼ÓÑ¶©µ¥
    //½¨Á¢md5strË÷Òý£¬ÓÐÔò¸üÐÂ£¬·ñÔò²åÈë
    //»ùÓÚMD5Ë÷Òýpatient+location+content(²¡ÈËÐÕÃû+µØÖ·+ÄÚÈÝ)==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdatedingdan(String doctorName,String type,String patient,String location,String dingdanhao,String statues,String urlhref,String content,String md5str)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into dingdan_table_resource(doctorName,type,patient,location,dingdanhao,statues,urlhref,content,md5str,updatetime) values('"+doctorName+"','"+type+"','"+patient+"','"+location+"','"
                    +dingdanhao+"','"+statues+"','"+urlhref+"','"+content+"','"+md5str+"','"+df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈë¶©µ¥ÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //¶ÁÈ¡Êý¾Ý==Éú²ú¸÷ÀàURL
    public static ResultSet readdbreourceUrl()
    {
        String sql="";
        try{
            sql = "select * from doctor_info_resource where id>160830";
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("¶ÁÈ¡Êý¾Ý¿âÔ´¿âÊý¾Ý¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return null;
        }
    }

    //¶ÁÈ¡paperÊý¾Ý==Éú²ú¸÷ÀàURL
    public static ResultSet readdbreourcepaperUrl()
    {
        String sql="";
        try{
            sql = "select * from doctorpaper_table_resource";
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("¶ÁÈ¡Êý¾Ý¿âÔ´¿âpaperÊý¾Ý¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return null;
        }
    }

    //¶ÁÈ¡»¼ÓÑ»áÊý¾Ý==Éú²ú¸÷ÀàURL
    public static ResultSet readdbreourcehuanyouhuiUrl()
    {
        String sql="";
        try{
            sql = "select * from huanyouhui_table_resource where (writenFlag=0 or writenFlag=-1)";
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("¶ÁÈ¡Êý¾Ý¿âÔ´¿â»¼ÓÑ»áÊý¾Ý¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return null;
        }
    }

    //¶ÁÈ¡»¼ÓÑ·þÎñÇøÊý¾Ý==Éú²ú¸÷ÀàURL
    public static ResultSet readdbreourcehuanyouserviceUrl(int id)
    {
        String sql="";
        try{
            sql = "select * from huanyouservice_table_resource where (writenFlag=-1 or writenFlag=0) and id>="+id +" and id<="+(id+10000);//Ã¿´Î×Ô¼ºÔö¼ÓÒ»Íò
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("¶ÁÈ¡Êý¾Ý¿âÔ´¿â»¼ÓÑ·þÎñÇøÊý¾Ý¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return null;
        }
    }

    //¶ÁÈ¡»¼ÓÑ·þÎñÇøÊý¾Ý==¹²¼ÆÊý¾ÝÐÐÊý
    public static ResultSet readdbreourcehuanyouserviceUrlCount()
    {
        String sql="";
        try{
            sql = "select id from huanyouservice_table_resource where writenFlag=-1 or writenFlag=0 order by id desc limit 1";//»ñµÃ×îÓÐÒ»Ìõ¼ÇÂ¼µÄIDºÅ
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("¶ÁÈ¡Êý¾Ý¿âÔ´¿â»¼ÓÑ·þÎñÇøÊý¾Ý¹²¼Æ×ÜÐÐÊý¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return null;
        }
    }

    //ÊÕ·Ñ±ê×¼
    //½¨Á¢md5strË÷Òý£¬ÓÐÔò¸üÐÂ£¬·ñÔò²åÈë
    //»ùÓÚMD5Ë÷ÒýdoctorName+type+doctorHospital+sfprice(Ò½ÉúÐÕÃû+ÀàÐÍ+Ò½Ôº+ÊÕ·Ñ±ê×¼)==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdatesfbz(String doctorName,String type,String doctorHospital,String sfprice,String doctormd5str,String md5str)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctor_resource_sfbz(doctorName,type,doctorHospital,sfprice,doctormd5str,md5str,createtime) values('"+doctorName+"','"+type+"','"+doctorHospital+"','"+sfprice+"','"
                    +doctormd5str+"','"+md5str+"','"+df.format(new Date())+"') ON DUPLICATE KEY UPDATE doctorName='"+doctorName+"',type='"+type+"',doctorHospital='"+doctorHospital+"',sfprice='"
                    +sfprice+"',doctormd5str='"+doctormd5str+"',md5str='"+md5str+"',createtime='"+df.format(new Date())+"'";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐò²åÈëÊÕ·Ñ±ê×¼¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }


    //Ò½ÉúÖ÷Ò³ÃæÍ³¼ÆÐÅÏ¢
    //½¨Á¢md5strË÷Òý£¬ÓÐÔò¸üÐÂ£¬·ñÔò²åÈë
    //»ùÓÚMD5Ë÷Òý
    //doctorName+doctormd5+thanksletter+liwu+gxvalue+
    //lovevalue+allvisitnum+yesterdaynum+allpaper+
    //allpatient+yesterdayendpatient+allendpatient+patientvote+lastonline+beginonline
    //==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdatestatistic(String doctorName,String doctormd5,String thanksletter,String liwu,String gxvalue,String lovevalue,
                                                   String allvisitnum,String yesterdaynum,String allpaper,String allpatient,String yesterdayendpatient,String allendpatient,
                                                   String patientvote,String lastonline,String beginonline,String uniqueMD5)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctor_statistic_value_ta(doctorName,doctormd5,thanksletter,liwu,gxvalue,lovevalue,allvisitnum,yesterdaynum,"
                    + "allpaper,allpatient,yesterdayendpatient,allendpatient,patientvote,lastonline,"
                    + "beginonline,uniqueMD5,updatetime) values('"+doctorName+"','"+doctormd5+"','"+thanksletter+"','"+liwu+"','"+gxvalue+"','"
                    +lovevalue+"','"+allvisitnum+"','"+yesterdaynum+"','"+allpaper+"','"+allpatient+"','"+yesterdayendpatient+"','"+allendpatient
                    +"','"+patientvote+"','"+lastonline+"','"+beginonline+"','"+uniqueMD5+"','"+df.format(new Date())+"') ON DUPLICATE KEY UPDATE doctorName='"+doctorName+
                    "',doctormd5='"+doctormd5+"',thanksletter='"+thanksletter+"',liwu='"+liwu+"',gxvalue='"
                    +gxvalue+"',lovevalue='"+lovevalue+"',allvisitnum='"+allvisitnum+"',yesterdaynum='"+yesterdaynum
                    +"',allpaper='"+allpaper+"',allpatient='"+allpatient+"',yesterdayendpatient='"+yesterdayendpatient
                    +"',allendpatient='"+allendpatient+"',patientvote='"+patientvote+"',lastonline='"+lastonline+"',beginonline='"+beginonline
                    +"',uniqueMD5='"+uniqueMD5+"',updatetime='"+df.format(new Date())+"'";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐòÒ½ÉúÖ÷Ò³ÃæÍ³¼ÆÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    public static boolean insertstatistic(String doctorName,String type,String md5str,String ismenzheng,String love,String donation,
                                          String allvisitnum,String yesterdaynum,String allpaper,String allpatience,String yesterdayadmin,String wechatadmin,
                                          String alladmin,String patiencevote,String thanksletter,String heartgift,String lastonline,String beginonline)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctor_statistic_value_ta(doctorName,type,md5str,ismenzheng,love,donation,allvisitnum,yesterdaynum,"
                    + "allpaper,allpatience,yesterdayadmin,wechatadmin,alladmin,patiencevote,thanksletter,heartgift,lastonline,"
                    + "beginonline,updatetime) values('"+doctorName+"','"+type+"','"+md5str+"','"+ismenzheng+"','"+love+"','"
                    +donation+"','"+allvisitnum+"','"+yesterdaynum+"','"+allpaper+"','"+allpatience+"','"+yesterdayadmin+"','"+wechatadmin
                    +"','"+alladmin+"','"+patiencevote+"','"+thanksletter+"','"+heartgift+"','"+lastonline+"','"+beginonline+"','"+df.format(new Date())+"')";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("´óµÚ¶þ°æ³ÌÐòÒ½ÉúÖ÷Ò³ÃæÍ³¼ÆÐÅÏ¢¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    /*================================================================================================*/
    //¾É°æÒ½ÉúÁÐ±í
	 /*================================================================================================*/
    //¾É°æÒ½ÉúÁÐ±í½¨Á¢±£´æ
    //½¨Á¢md5strË÷Òý£¬ÓÐÔò¸üÐÂ£¬·ñÔò²åÈë
    //Ò½ÉúºÍ¸ÐÐËÈ¤Ò½ÉúºÍÍÆ¼öÒ½ÉúMD5Ë÷Òý²ÉÓÃ£ºdoctorname+doctorhospital(Ò½ÉúÐÕÃû+Ò½Ôº¿ÆÊÒ)
    //»ùÓÚMD5Ë÷Òýdoctorname+doctorhospital+doctorgoodat+doctorovoted(Ò½ÉúÐÕÃû+Ò½Ôº¿ÆÊÒ+ÉÃ³¤+Í¶Æ±)==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdateolddoctorlist(String doctorname,String doctorhomepage,String doctormd5index,String doctorhospital,String doctorgoodat,String doctorovoted,String doctorsfbz,String doctoruniquemd5)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctorlist_oldTable_resource(doctorname,doctorhomepage,doctormd5index,doctorhospital,doctorgoodat,doctorovoted,doctorsfbz,doctoruniquemd5,updatetime) values('"+doctorname+"','"+doctorhomepage+"','"+doctormd5index+"','"+doctorhospital+"','"
                    +doctorgoodat+"','"+doctorovoted+"','"+doctorsfbz+"','"+doctoruniquemd5+"','"+df.format(new Date())+"') ON DUPLICATE KEY UPDATE doctorname='"+doctorname+"',doctorhomepage='"+doctorhomepage+"',doctormd5index='"+doctormd5index+"',doctorhospital='"
                    +doctorhospital+"',doctorgoodat='"+doctorgoodat+"',doctorovoted='"+doctorovoted+"',doctorsfbz='"+doctorsfbz+"',doctoruniquemd5='"+doctoruniquemd5+"',updatetime='"+df.format(new Date())+"'";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("µÚ¶þ°æ³ÌÐò¾É°æ±¾Ò½ÉúÁÐ±í²åÈë¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //¾É°æÒ½ÉúÍÆ¼öÒ½Éú½¨Á¢±£´æ
    //Ò½ÉúºÍ¸ÐÐËÈ¤Ò½ÉúºÍÍÆ¼öÒ½ÉúMD5Ë÷Òý²ÉÓÃ£ºdoctorname+doctorhospital(Ò½ÉúÐÕÃû+Ò½Ôº¿ÆÊÒ)ÓÉÁÐ±í´«Èë
    //»ùÓÚMD5Ë÷Òýdoctorname+doctorhospital+doctorgoodat+doctorovoted(Ò½ÉúÐÕÃû+Ò½Ôº¿ÆÊÒ+ÉÃ³¤+Í¶Æ±)==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdateolddoctorrecommendation(String doctorname,String doctorhomepage,String doctormd5index,String doctorhospital,String doctorgoodat,String doctorovoted,String doctorsfbz,String doctoruniquemd5)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctorlist_oldTable_recommendation_resource(doctorname,doctorhomepage,doctormd5index,doctorhospital,doctorgoodat,doctorovoted,doctorsfbz,doctoruniquemd5,updatetime) values('"+doctorname+"','"+doctorhomepage+"','"+doctormd5index+"','"+doctorhospital+"','"
                    +doctorgoodat+"','"+doctorovoted+"','"+doctorsfbz+"','"+doctoruniquemd5+"','"+df.format(new Date())+"') ON DUPLICATE KEY UPDATE doctorname='"+doctorname+"',doctorhomepage='"+doctorhomepage+"',doctormd5index='"+doctormd5index+"',doctorhospital='"
                    +doctorhospital+"',doctorgoodat='"+doctorgoodat+"',doctorovoted='"+doctorovoted+"',doctorsfbz='"+doctorsfbz+"',doctoruniquemd5='"+doctoruniquemd5+"',updatetime='"+df.format(new Date())+"'";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("µÚ¶þ°æ³ÌÐò¾É°æ±¾Ò½ÉúÍÆ¼öÒ½ÉúÁÐ±í²åÈë¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //¾É°æÒ½Éú¸ÐÐËÈ¤Ò½Éú½¨Á¢±£´æ
    //Ò½ÉúºÍ¸ÐÐËÈ¤Ò½ÉúºÍÍÆ¼öÒ½ÉúMD5Ë÷Òý²ÉÓÃ£ºdoctorname+doctorhospital(Ò½ÉúÐÕÃû+Ò½Ôº¿ÆÊÒ)ÓÉÁÐ±í´«Èë
    //»ùÓÚMD5Ë÷Òýdoctorname+doctorhospital+doctorgoodat+doctorovoted(Ò½ÉúÐÕÃû+Ò½Ôº¿ÆÊÒ+ÉÃ³¤+Í¶Æ±)==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdateolddoctorinteresting(String doctorname,String doctorhomepage,String doctormd5index,String doctorhospital,String doctorgoodat,String doctorovoted,String doctorsfbz,String doctoruniquemd5)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into doctorlist_oldTable_interesting_resource(doctorname,doctorhomepage,doctormd5index,doctorhospital,doctorgoodat,doctorovoted,doctorsfbz,doctoruniquemd5,updatetime) values('"+doctorname+"','"+doctorhomepage+"','"+doctormd5index+"','"+doctorhospital+"','"
                    +doctorgoodat+"','"+doctorovoted+"','"+doctorsfbz+"','"+doctoruniquemd5+"','"+df.format(new Date())+"') ON DUPLICATE KEY UPDATE doctorname='"+doctorname+"',doctorhomepage='"+doctorhomepage+"',doctormd5index='"+doctormd5index+"',doctorhospital='"
                    +doctorhospital+"',doctorgoodat='"+doctorgoodat+"',doctorovoted='"+doctorovoted+"',doctorsfbz='"+doctorsfbz+"',doctoruniquemd5='"+doctoruniquemd5+"',updatetime='"+df.format(new Date())+"'";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("µÚ¶þ°æ³ÌÐò¾É°æ±¾Ò½Éú¸ÐÐËÈ¤Ò½ÉúÁÐ±í²åÈë¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //¶ÁÈ¡Êý¾Ý==Éú²ú¸÷ÀàURL
    public static ResultSet readdbreourceoldUrl()
    {
        String sql="";
        try{
            sql = "select doctorhomepage,doctormd5index from doctorlist_oldTable_resource";
            return DBOperation.execSQL(sql);
        }catch(Exception ee){
            logger.error("¶ÁÈ¡¾É°æ±¾Ò½ÉúÊý¾Ý¿âÔ´¿âÊý¾Ý¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return null;
        }
    }
    /*================================================================================================*/
    //½â¾ö»¼ÓÑ»áºÍ»¼Õß·þÎñÇøÊý¾Ý¼ÇÂ¼ÌõÄ¿Ì«¶à£¬ÎÞ·¨×öÒ»´ÎÐÔµü´úÀëÏß¿âÎÊÌâ£¨×îÐÂ¸ü¸Ä£©
	 /*================================================================================================*/
    //½«»¼Õß·þÎñÇøÄ³Ìõ¼ÇÂ¼ÉèÖÃÎªÒÑ¾­´¦ÀíÍê³ÉwirtenFlag=1
    public boolean setHuanyouserviceWritenFlag(String md5str)
    {
        String sql="";
        try{
            sql = "update huanyouservice_table_resource set writenFlag=1 where md5str='"+md5str+"'";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("½«»¼Õß·þÎñÇøÄ³Ìõ¼ÇÂ¼ÉèÖÃÎªÒÑ¾­´¦ÀíÍê³ÉwirtenFlag=1¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //½«»¼Õß·þÎñÇøÈ«²¿¼ÇÂ¼¶¼ÉèÖÃÎªÎ´Íê³É×´Ì¬£¬½øÐÐÖØÐÂ´¦Àí£¨ÐÂÒ»ÂÖµü´ú£©
    public boolean setallHuanyouserviceWritenFlag()
    {
        String sql="";
        try{
            sql = "update huanyouservice_table_resource set writenFlag=-1";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("½«»¼Õß·þÎñÇøÈ«²¿¼ÇÂ¼¶¼ÉèÖÃÎªÎ´Íê³É×´Ì¬£¬½øÐÐÖØÐÂ´¦Àí£¨ÐÂÒ»ÂÖµü´ú£©¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //½«»¼ÓÑ»áÄ³Ìõ¼ÇÂ¼ÉèÖÃÎªÒÑ¾­´¦ÀíÍê³ÉwirtenFlag=1
    public boolean setHuanyouhuiWritenFlag(String md5str)
    {
        String sql="";
        try{
            sql = "update huanyouhui_table_resource set writenFlag=1 where md5str='"+md5str+"'";
            System.out.println(sql);
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("½«»¼ÓÑ»áÄ³Ìõ¼ÇÂ¼ÉèÖÃÎªÒÑ¾­´¦ÀíÍê³ÉwirtenFlag=1¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //½«»¼ÓÑ»áÈ«²¿¼ÇÂ¼¶¼ÉèÖÃÎªÎ´Íê³É×´Ì¬£¬½øÐÐÖØÐÂ´¦Àí£¨ÐÂÒ»ÂÖµü´ú£©
    public boolean setallHuanyouhuiWritenFlag()
    {
        String sql="";
        try{
            sql = "update huanyouhui_table_resource set writenFlag=-1";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("½«»¼ÓÑ»áÈ«²¿¼ÇÂ¼¶¼ÉèÖÃÎªÎ´Íê³É×´Ì¬£¬½øÐÐÖØÐÂ´¦Àí£¨ÐÂÒ»ÂÖµü´ú£©¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

	 /*================================================================================================*/
	 /*================================================================================================*/


    //ÐÂ°æ¸ÐÐ»ÐÅÊý¾Ý¿â²åÈë
    //Ò½ÉúMD5Ë÷Òý²ÉÓÃ£ºdoctorname+md5id(Ò½ÉúÐÕÃû+Ò½ÉúID)ÓÉÁÐ±í´«Èë
    //»ùÓÚMD5Ë÷Òýdoctorname,type,md5id,patient,disease,Vitime,content,lx,td,rem,rev,reference==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdatenewthanksletter(String doctorname,String type,String md5id,String patient,String disease,String Vitime,String content,String lx,String td,String rem,String rev,String reference,String md5str)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into thanksletterNew_table_resource(doctorname,type,md5id,patient,disease,Vitime,content,lx,td,rem,rev,reference,md5str,updatetime) values('"+doctorname+"','"+type+"','"+md5id+"','"+patient+"','"
                    +disease+"','"+Vitime+"','"+content+"','"+lx+"','"+td+"','"+rem+"','"+rev+"','"+reference+"','"+md5str+"','"+df.format(new Date())+"') ON DUPLICATE KEY UPDATE doctorname='"+doctorname+"',type='"+type+"',md5id='"+md5id+"',patient='"
                    +patient+"',disease='"+disease+"',Vitime='"+Vitime+"',content='"+content+"',lx='"+lx+"',td='"+td+"',rem='"+rem+"',rev='"+rev+"',reference='"+reference+"',md5str='"+md5str+"',updatetime='"+df.format(new Date())+"'";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("ÐÂ³ÌÐòÐÂ°æ¸ÐÐ»ÐÅÊý¾Ý¿â²åÈëÁÐ±í²åÈë¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }

    //ÐÂ°æ¿´²¡¾­ÑéÊý¾Ý¿â²åÈë
    //Ò½ÉúMD5Ë÷Òý²ÉÓÃ£ºdoctorname+md5id(Ò½ÉúÐÕÃû+Ò½ÉúID)ÓÉÁÐ±í´«Èë
    //»ùÓÚMD5Ë÷Òýdoctorname,type,md5id,patient,disease,Vitime,content,lx,td,rem,rev,reference==ÔöÁ¿Ê½´¦Àí
    public static boolean insertandupdateKBJY(String doctorname,String type,String md5id,String patient,String disease,String Vitime,String content,String lx,String td,String rem,String rev,String reference,String md5str)
    {
        String sql="";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ÉèÖÃÈÕÆÚ¸ñÊ½
            sql="insert into kbjy_table_resource(doctorname,type,md5id,patient,disease,Vitime,content,lx,td,rem,rev,reference,md5str,updatetime) values('"+doctorname+"','"+type+"','"+md5id+"','"+patient+"','"
                    +disease+"','"+Vitime+"','"+content+"','"+lx+"','"+td+"','"+rem+"','"+rev+"','"+reference+"','"+md5str+"','"+df.format(new Date())+"') ON DUPLICATE KEY UPDATE doctorname='"+doctorname+"',type='"+type+"',md5id='"+md5id+"',patient='"
                    +patient+"',disease='"+disease+"',Vitime='"+Vitime+"',content='"+content+"',lx='"+lx+"',td='"+td+"',rem='"+rem+"',rev='"+rev+"',reference='"+reference+"',md5str='"+md5str+"',updatetime='"+df.format(new Date())+"'";
            return DBOperation.exec(sql);
        }catch(Exception ee){
            logger.error("ÐÂ³ÌÐòÐÂ°æ¸ÐÐ»ÐÅÊý¾Ý¿â²åÈëÁÐ±í²åÈë¡ª¡ªÑÏÖØÒì³£,URL="+sql+"----ee>>>"+ee.toString());
            return false;
        }
    }
	 /*================================================================================================*/
	 /*================================================================================================*/

}
