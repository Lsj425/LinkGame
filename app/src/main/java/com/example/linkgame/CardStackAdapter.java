package com.example.linkgame;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

// CardStackView适配器类
public class CardStackAdapter extends StackAdapter<Integer> {

    public CardStackAdapter(Context context) {
        super(context);
    }

    // 调用onBind()加载布局
    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(data, position);
        }
    }

    // 加载卡片的item布局
    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default:
                view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
                return new ColorItemViewHolder(view);
        }
    }

    // 获取子项样式类型
    @Override
    public int getItemViewType(int position) {
        return R.layout.list_card_item;
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        ImageView mProfile;
        TextView mNickName;
        TextView mName;
        TextView mBrief;
        TextView mIntro;
        TextView mMore;

        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            mProfile = view.findViewById(R.id.list_card_profile);
            mNickName = view.findViewById(R.id.list_card_nickname);
            mName = view.findViewById(R.id.list_card_name);
            mBrief = view.findViewById(R.id.list_card_brief);
            mIntro = view.findViewById(R.id.chacter_intro);
            mMore = view.findViewById(R.id.chacter_more);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);

        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position + 1));

            initItem(position);
        }

        // 初始化子项内容
        public void initItem(int position){
            switch (position) {
                case 0:
                    mProfile.setImageResource(R.drawable.card_item_antman);
                    mNickName.setText("蚁人(Ant-Man)");
                    mName.setText("汉克·皮姆");
                    mBrief.setText("\"复仇者联盟创始人之一\"");
                    mIntro.setText("         蚁人（Ant-Man）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《惊异故事》（Tales to Astonish）第27期（1962年1月），" +
                            "由编剧斯坦·李以及画家杰克·科比联合创造，共有三代。" +
                            "其中最为人们所熟知的是初代蚁人汉克·皮姆，他是复仇者联盟的创始人之一，" +
                            "漫威电影宇宙中现任蚁人为二代蚁人斯科特·朗。\n" +
                            "         蚁人的英雄背景起源自生物化学家汉克·皮姆（Hank Pym）发现了一种" +
                            "可以改变物体大小的亚原子粒子，他以自己的名称为此命名——皮姆粒子（Pym particles）。" +
                            "后来他以此为基石打造了蚁人制服，并发明了一个可以跟蚂蚁沟通的配套电子头盔，" +
                            "从而使自己拥有身体自由变化大小和与控制所有种类蚂蚁的能力，成为初代蚁人。");
                    mMore.setText("知名对手：蛋头人、龙卷风、豪猪" +
                            "\n相关电影：《蚁人》、《蚁人2：黄蜂女现身》、《美国队长3：内战》");
                    break;
                case 1:
                    mProfile.setImageResource(R.drawable.card_item_black_panther);
                    mNickName.setText("黑豹(Black Panther)");
                    mName.setText("特查拉");
                    mBrief.setText("\"瓦坎达国王\"");
                    mIntro.setText("         黑豹（Black Panther）是美国漫威漫画旗的下超级英雄，" +
                            "初次登场于《神奇四侠》（Fantastic Four）第52期（1966年7月），" +
                            "由编辑斯坦·李和漫画家杰克·科比共同创造，" +
                            "是第一位在主流美国漫画公司登场的非讽刺黑人超级英雄。\n" +
                            "         漫威电影宇宙现任黑豹本名特查拉（T'Challa），青年时曾在英国留学，" +
                            "并以相当优异的成绩毕业于牛津大学，在其父——前任黑豹特查卡（T'Chaka）死后，" +
                            "回国接任黑豹的称号成为瓦坎达国王/守护者" +
                            "（每一代守护者都要进食一块心形药草以得到远超常人的速度、力量、体能、耐力以及敏锐度，" +
                            "并配有能够吸收和释放部分能量的黑豹战服）。\n" +
                            "         关于瓦坎达，这是漫威宇宙中虚构的一个国家，" +
                            "位于非洲东北部。历史上曾有陨石落在瓦坎达而带来大量稀有金属振金（Vibranium），" +
                            "该金属是漫威宇宙中最坚硬的材质之一，在硬度上仅次于艾德曼合金，" +
                            "在防御力上因为能够吸收热能和动能的特性而位居第一。" +
                            "瓦坎达长期处于与世隔绝状态，仅凭秘密出售振金以此发展科技而成为了先进国家。");
                    mMore.setText("知名对手：魔人猿木巴库、金钱豹基尔蒙格" +
                            "\n相关电影：《黑豹》、《美国队长3：内战》、《复仇者联盟3：无限战争》");
                    break;
                case 2:
                    mProfile.setImageResource(R.drawable.card_item_blackcat);
                    mNickName.setText("黑猫(Black Cat)");
                    mName.setText("菲丽西亚·哈代");
                    mBrief.setText("\"世纪大盗\"");
                    mIntro.setText("         菲丽西亚·哈代（Felicia Hardy）即黑猫（Black Cat），" +
                            "是美国漫威漫画旗下的“反英雄”，初次登场于《神奇蜘蛛侠》" +
                            "（The Amazing Spider-Man）第194期（1979年7月），" +
                            "由编剧Marv Wolfman和画师Keith Pollard创造。\n" +
                            "         她是大盗灵猫（The Cat）的女儿，在遭遇一件难以原谅的伤害后，" +
                            "她开始学习各种格斗术和战斗技巧，并且决定追随父亲的脚步，成为一名盗贼。" +
                            "她的动机摇摆不定，亦正亦邪，后来加入超级英雄团体捍卫者联盟。\n" +
                            "         黑猫拥有超人的平衡力、灵活性和力量，最高可举起约800磅（363公斤）重物。" +
                            "黑猫的制服可以提升她的物理属性，使她可以做出各种类似猫的动作。" +
                            "制服的手套中藏有可伸缩的利爪，可用作武器攻击对手。黑猫通常携带一条带有抓钩的装备，" +
                            "内置弹射装置。使她可以像蜘蛛侠和超胆侠一样，在建筑物之间飘荡移动。");
                    mMore.setText("知名对手/情人：蜘蛛侠" +
                            "\n相关电影：暂无");
                    break;
                case 3:
                    mProfile.setImageResource(R.drawable.card_item_captain_america);
                    mNickName.setText("美国队长(Captain America)");
                    mName.setText("史蒂夫·罗杰斯");
                    mBrief.setText("\"自由与正义的化身\"");
                    mIntro.setText("         美国队长（Captain America）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《美国队长》（Captain America Comics）第1期（1941年3月），" +
                            "由乔·西蒙和杰克·科比联合创造，被视为美国精神——自由与正义的象征。\n" +
                            "         美国队长总共有7任，最出名的则是初代美国队长史蒂夫·罗杰斯（Steve Rogers），" +
                            "1918年7月4日出生于美国纽约布鲁克林区，原本是一名身体瘦弱的年轻人，" +
                            "在接受美国政府的实验改造后变成了“超级士兵”，" +
                            "使其力量、速度、耐力等各项体能都远超于常人，达到了人类所能达到的潜能极限，" +
                            "且还被美国政府赋予了由世界上最坚硬的金属之一振金制成的盾牌，" +
                            "从此史蒂夫以美国队长的身份，为美国及世界在二战中立下赫赫战功。" +
                            "后来在二战尾声的一次行动中，美国队长与宿敌红骷髅展开激战，" +
                            "并掉入大海之中被冰封近70年，直到被神盾局发现并解冻后才加入了复仇者联盟。\n" +
                            "         美国队长的创作背景源自第二次世界大战，欧亚战场打得如火如荼，" +
                            "远在大洋彼岸的美国此时却尚未参战。尽管如此，国内反法西斯的呼声也越来越强烈。" +
                            "在这风雨欲来的多事之秋，漫画公司（Timely Comics，后来的Marvel）" +
                            "推出了这位最具“主旋律”的超级英雄。");
                    mMore.setText("知名对手：红骷髅、泽莫男爵、交叉骨、九头蛇组织" +
                            "\n相关电影：《美国队长：复仇者先锋》、《美国队长2：冬日战士》、" +
                            "《美国队长3：内战》、《复仇者联盟》、《复仇者联盟2：奥创纪元》、" +
                            "《复仇者联盟3：无限战争》");
                    break;
                case 4:
                    mProfile.setImageResource(R.drawable.card_item_colossus);
                    mNickName.setText("钢力士(Colossus)");
                    mName.setText("彼得·拉斯普廷");
                    mBrief.setText("\"X战警里的毕加索\"");
                    mIntro.setText("         钢力士（Colossus）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《Giant-Size X-Men》第1期（1975年5月）。" +
                            "本名皮奥特· 尼古拉耶维奇·拉斯普廷/彼得·拉斯普廷（Piotr Nikolaievitch Rasputin/Peter Rasputin），" +
                            "是一个有着艺术家灵魂的变种人，他的为人十分的友善，是X战警里的毕加索，" +
                            "比起战斗来说，他更喜欢为队友画肖像。\n" +
                            "         钢力士作为变种人，可以将身体的全部组织转化为一种类似钢的物质，" +
                            "并且因此而得到超乎常人的体力以及超强的防御力。在变异形态下他不需要呼吸、进食以及休息。" +
                            "但他无法部分或选择性装甲化：他的身体要么完全转化，要么一点儿也不转化。" +
                            "在失去意识的情况下他会恢复正常状态。");
                    mMore.setText("知名对手：红坦克" +
                            "\n相关电影：《X战警2》、《X战警3：背水一战》、《X战警：逆转未来》、《死侍》、《死侍2：我爱我家》");
                    break;
                case 5:
                    mProfile.setImageResource(R.drawable.card_item_cyclops);
                    mNickName.setText("镭射眼(Cyclops)");
                    mName.setText("斯科特·萨默斯");
                    mBrief.setText("\"第二代X战警领袖\"");
                    mIntro.setText("         镭射眼（Cyclops）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《X战警》（The X-Men）第1期（1963年9月）。" +
                            "本名斯科特·萨默斯（Scott Summers），是一名变种人，" +
                            "天生双眼就拥有发射红色冲击波的能力 ，后被X教授招募加入X战警，" +
                            "由于他拥有极其出色的领袖才能，所以长期担任X战警的队长，" +
                            "后接任X教授成为X学院的院长，并成为了几乎所有变种人的领袖。\n" +
                            "         作为一个领袖，斯科特的理念和万磁王，X教授都不同，" +
                            "是温和的现实主义，一方面，他主张与人类友好相处，并进行维护正义，" +
                            "保护人类的各种行动。另一方面，为了保护仅存的少数变种人，" +
                            "他对那些屠杀了成千上万变种人的极端组织采取了不择手段的方式进行毁灭。" +
                            "为了保证变种人不被灭亡，他采取了一些有争议的行动：武装训练学生，" +
                            "命令学生上场作战。\n" +
                            "这些措施起到了积极的效果，却分别与金刚狼，野兽等人的理念严重冲突，" +
                            "并由此导致了X战警于2011年分裂。");
                    mMore.setText("知名对手：变种人兄弟会" +
                            "\n相关电影：《X战警1》、《X战警2》、《X战警3：背水一战》、《X战警：天启》");
                    break;
                case 6:
                    mProfile.setImageResource(R.drawable.card_item_deadpool);
                    mNickName.setText("死侍(Deadpool)");
                    mName.setText("韦德·温斯顿·威尔逊");
                    mBrief.setText("\"至贱至萌——嘴炮王\"");
                    mIntro.setText("         死侍（Deadpool）是美国漫威漫画旗下的“反英雄”，" +
                            "初次登场于《新变种人》（New Mutants）第98期 （1991年2月）。" +
                            "本名韦德·温斯顿·威尔逊（Wade Winston Wilson），" +
                            "原是一名身经百战的特种兵，为了治愈身患癌症的自己而参加X武器（Weapon X）计划，" +
                            "并被注入了金刚狼的基因得以延续生命，代价是他的容貌被毁。" +
                            "因被宇宙五大神明中的死亡女神爱慕而被灭霸诅咒成不死之身，" +
                            "后灭霸与死侍联手救出死亡女神收回了诅咒。他使得一手双刀，双枪，" +
                            "拥有远超于金刚狼的治愈能力，还有一个可以令自身瞬间移动的腰带。\n" +
                            "         他与其他英雄有最大的两处不同：第一点，他是一位非常非常话多的角色，" +
                            "在看《死侍》系列漫画的时候，也许你会产生错觉，你不是在看一本漫画，" +
                            "你是在读一本纯文字的小说，而且所说的话很多驴唇不对马嘴又夹乱七八糟的东西，" +
                            "让很多读者郁闷。他本身非常爱胡扯，仗着自己具有不死之身整天胡说八道，" +
                            "令许多英雄远离他，其毒舌功夫可见一斑。另一个不同，" +
                            "死侍知道自己身处漫画世界，所以死侍处于一种“众人皆醉我独醒”的状态。");
                    mMore.setText("知名对手：整个漫威宇宙" +
                            "\n相关电影：《金刚狼1》、《死侍》、《死侍2：我爱我家》");
                    break;
                case 7:
                    mProfile.setImageResource(R.drawable.card_item_doctor_strange);
                    mNickName.setText("奇异博士(Doctor Strange)");
                    mName.setText("史蒂芬·斯特兰奇");
                    mBrief.setText("\"光照会创始人之一\"");
                    mIntro.setText("         奇异博士（Doctor Strange）是美国漫威漫画旗下超级英雄，" +
                            "初次登场于《奇异故事》（Strange Tales）第110期 （1963年7月），" +
                            "由斯坦·李和史蒂夫·迪特科联合创造。\n" +
                            "         本名史蒂芬·斯特兰奇（Stephen Strange），" +
                            "原本是一名优秀的神经外科医生，因一次车祸导致其双手再也无法使用手术刀正常工作，" +
                            "为了治好自己的双手史蒂芬寻遍世界各国名医都徒劳无果，" +
                            "绝望的他只能来到喜马拉雅山上拜访传说中的魔法师古一（Ancient One），" +
                            "却被古一看中，收其为徒，传授他如何运用精神和咒语使用魔法，" +
                            "在学习魔法的过程中，史蒂芬的双手也逐渐恢复，出师后的他化身奇异博士守卫世界，" +
                            "在师父古一阵亡后接替其位置，成为至尊法师，还加入了复仇者联盟，" +
                            "与钢铁侠、X教授等联合创建了光照会。");
                    mMore.setText("知名对手：莫度男爵、多玛姆" +
                            "\n相关电影：《奇异博士》、《雷神3：诸神黄昏》、《复仇者联盟3：无限战争》");
                    break;
                case 8:
                    mProfile.setImageResource(R.drawable.card_item_galactus);
                    mNickName.setText("行星吞噬者(Galactus)");
                    mName.setText("加兰");
                    mBrief.setText("\"漫威五大神明之一\"");
                    mIntro.setText("         行星吞噬者（Galactus）是美国漫威漫画旗下的超级反派，" +
                            "初次登场于《神奇四侠》（Fantastic Four）第48期。本名加兰（Galan），" +
                            "是漫威漫画宇宙的五大神明之一。\n" +
                            "         行星吞噬者人如其名，需要摄取行星能力以此维持生命。" +
                            "在漫长的岁月里，他只通过感知生命力摄干了那些无人居住的行星，" +
                            "然而，随着年龄的增长，他需要能量的时间间隔越来越短，" +
                            "且他若发现如果找不到其它无人行星有他需要的能源，" +
                            "就必须通过感知种族生命力摄取有居民居住的行星能量以维持他的生命。\n" +
                            "         他手下有四个使者，银影侠、暴君使者、焰皇和星尘为其寻找合适的吞噬星球。" +
                            "因为在许多漫威大事件中，该神明显得过于不堪一击，经常被众漫画粉丝嘲讽。");
                    mMore.setText("知名对手：编剧" +
                            "\n相关电影：暂无");
                    break;
                case 9:
                    mProfile.setImageResource(R.drawable.card_item_ghost_rider);
                    mNickName.setText("恶灵骑士(Ghost Rider)");
                    mName.setText("强尼·布雷泽");
                    mBrief.setText("\"罪恶审判者\"");
                    mIntro.setText("         恶灵骑士（Ghost Rider）是美国漫威漫画旗下的超级英雄，" +
                            "由作者兼编辑罗伊·托马斯（Roy Thomas）和作者加里·弗里德里希（Gary Friedrich）" +
                            "以及画家麦克·普鲁格（Mike Ploog）联合创造，" +
                            "初次登场于《漫威聚光灯》（Marvel Spotlight）第5期（1972年8月）。\n" +
                            "         初代恶灵骑士强尼·布雷泽（Johnny Blaze），" +
                            "原是一名摩托车特技车手，" +
                            "为了拯救患癌养父便和魔鬼墨菲斯托做了交易而被邪灵扎坦诺斯附身，" +
                            "随后得到了控制地狱火的力量和可以让怀有罪恶者感受到剧烈痛苦的审判之眼。");
                    mMore.setText("知名对手：墨菲斯托、扎坦诺斯" +
                            "\n相关电影：《恶灵骑士》、《恶灵骑士2：复仇时刻》");
                    break;
                case 10:
                    mProfile.setImageResource(R.drawable.card_item_hawkeye);
                    mNickName.setText("鹰眼(Hawkeye)");
                    mName.setText("克林顿·弗朗西斯·巴顿");
                    mBrief.setText("\"箭无虚发、百步穿杨\"");
                    mIntro.setText("         鹰眼（Hawkeye）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《悬疑故事》（Tales of Suspense）第57期（1964年9月），" +
                            "由斯坦·李和唐·海克联合创造。\n" +
                            "         本名克林顿·弗朗西斯·巴顿（Clinton Francis Barton），" +
                            "小名克林特（Clint），曾化名为歌利亚（Goliath）与浪人（Ronin），" +
                            "是个在马戏团长大的孤儿，师从剑客（Swordsman）和捷射（Trick Shot），" +
                            "天赋异常，小时候便获得“鹰眼“和“世界最佳狙击手”的称号，" +
                            "因在某次演出时看到钢铁侠救人的一幕后，决定利用自己的能力成为超级英雄，" +
                            "后加入复仇者联盟，成为其中重要的一员。");
                    mMore.setText("知名对手：剑客" +
                            "\n相关电影：《美国队长3：内战》、《复仇者联盟》、《复仇者联盟2：奥创纪元》");
                    break;
                case 11:
                    mProfile.setImageResource(R.drawable.card_item_hulk);
                    mNickName.setText("绿巨人(Hulk)");
                    mName.setText("布鲁斯·班纳");
                    mBrief.setText("\"越愤怒越强大\"");
                    mIntro.setText("         绿巨人浩克（Hulk）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《不可思议的浩克》（The Incredible Hulk）第一期（1962年5月），" +
                            "由斯坦·李和杰克·科比联合创造。\n" +
                            "         本名罗伯特·布鲁斯·班纳（Robert Bruce Banner），" +
                            "是世界著名的物理学家，在一次意外中被自己制造出的伽玛炸弹" +
                            "（Gamma Bomb）的放射线大量辐射，身体产生异变，" +
                            "后每当他情绪激动心跳加速的时候就会变成名为浩克的绿色怪物。" +
                            "由于变身后往往不受控制，所以为了不伤害身边的人，" +
                            "班纳游走于世界各地寻找控制愤怒的方法，" +
                            "即便如此班纳的浩克身份还是时常会造成毁灭性的破坏，" +
                            "因此常成为警方与有关单位追捕的对象。" +
                            "在漫威电影宇宙中加入复仇者联盟，并与托尼·斯塔克共同研制反浩克装甲，" +
                            "以此牵制变身后情绪不稳定的浩克。");
                    mMore.setText("知名对手：憎恶、大头目、U家军" +
                            "\n相关电影：《绿巨人》、《雷神：诸神黄昏》、《复仇者联盟》、" +
                            "《复仇者联盟2：奥创纪元》、《复仇者联盟3：无限战争》");
                    break;
                case 12:
                    mProfile.setImageResource(R.drawable.card_item_ironman);
                    mNickName.setText("钢铁侠(Iron-Man)");
                    mName.setText("托尼·斯塔克");
                    mBrief.setText("\"凡人之躯，比肩神明\"");
                    mIntro.setText("         托尼·斯塔克（Tony Stark）即钢铁侠（Iron Man），" +
                            "是美国漫威漫画旗下超级英雄，初次登场于《悬疑故事》（Tales of Suspense）" +
                            "第39期（1963年3月），由斯坦·李、赖瑞·理柏、唐·赫克和杰克·科比联合创造。\n" +
                            "         全名安东尼·爱德华·托尼·斯塔克（Anthony Edward Tony Stark），" +
                            "是斯塔克工业（STARK INDUSTRIES）的董事长，" +
                            "同时还是一名自负的发明家、冒险家、亿万富豪和花花公子。" +
                            "因一场阴谋绑架中，胸部遭弹片穿入，生命危在旦夕，为了挽救自己的性命，" +
                            "在同被绑架的物理学家殷森（Yin Sen）的协助下托尼造出了防止弹片侵入心脏的方舟反应炉从而逃过一劫，" +
                            "利用方舟反应炉作为能量运转的来源，他暗中制造了一套高科技战衣杀出重围后逃脱。" +
                            "此后他对钢铁战衣不断升级改造，截至目前为止，在漫威电影宇宙中，" +
                            "托尼一共研发了50套钢铁战衣，参与了绝大多数大事件。\n" +
                            "         托尼斯塔克的人物原型取材自历史真实人物霍华德·修斯（1905-1976），" +
                            "身负企业家、飞行员、电影制片人、导演、演员等多个光环，" +
                            "是那个时代最多姿多彩的人物之一。");
                    mMore.setText("知名对手：满大人、绯红机甲、贾斯汀·汉默" +
                            "\n相关电影：《钢铁侠》、《钢铁侠2》、《钢铁侠3》、《美国队长3：内战》、" +
                            "《蜘蛛侠：返校日》、《复仇者联盟》、《复仇者联盟2：奥创纪元》、" +
                            "《复仇者联盟3：无限战争》");
                    break;
                case 13:
                    mProfile.setImageResource(R.drawable.card_item_loki);
                    mNickName.setText("洛基(Loki)");
                    mName.setText("洛基·奥丁森");
                    mBrief.setText("\"诡计之神\"");
                    mIntro.setText("         洛基·奥丁森（Loki Odinson）是美国漫威漫画旗下的“反英雄”，" +
                            "初次登场于《Venus》第6期（1949年8月）。简称洛基（Loki），" +
                            "是北欧神话中的火神和恶作剧之神。他的亲生父母都是冰霜巨人族，" +
                            "在阿斯加德和冰霜巨人的战争后，被众神之王奥丁当做亲生骨肉收养，" +
                            "从小和雷神托尔一起长大。一直窥视众神之王的宝座，" +
                            "并因此屡次与哥哥雷神索尔以及他的朋友复仇者联盟为敌。\n" +
                            "         洛基拥有超强的体能和力量，能够举起50吨的重物。" +
                            "洛基的寿命远超人类，抗常规损伤，对地球一切疾病、毒药免疫，" +
                            "普通的身体伤害洛基都能快速恢复。他同时也是是阿斯加德最强大的巫师，" +
                            "他有着许多神奇的能力，包括变形、星体投射、分子重排、" +
                            "能量爆炸、制造幻想、飞行（通过悬浮）、心灵感应、催眠和传送。");
                    mMore.setText("知名对手/亲人：索尔" +
                            "\n相关电影：《雷神》、《雷神2：黑暗世界》、《雷神3：诸神黄昏》、" +
                            "《复仇者联盟》、《复仇者联盟3：无限战争》");
                    break;
                case 14:
                    mProfile.setImageResource(R.drawable.card_item_magneto);
                    mNickName.setText("万磁王(Magneto)");
                    mName.setText("马克思·艾森哈特");
                    mBrief.setText("\"变种人兄弟会领袖\"");
                    mIntro.setText("         万磁王（Magneto）是美国漫威漫画旗下的超级反派，" +
                            "初次登场于《X战警》第1期（1963年9月），是X战警的头号死敌。\n" +
                            "         本名马克思·艾森哈特（Max Eisenhardt），是一名变种人，" +
                            "可以控制地球磁场，与X教授是多年好友，后因理念不合而与X教授分道扬镳，" +
                            "创建了变种人兄弟会。因为经历过残酷的二战和人类对变种人的疯狂打击，" +
                            "使得万磁王极度憎恨人类，并屡次与阻止他的X战警、复仇者联盟等超级英雄团队为敌。");
                    mMore.setText("知名对手：X战警、X教授（挚友）" +
                            "\n相关电影：《X战警1》、《X战警2》、《X战警3：背水一战》、" +
                            "《X战警：第一战》、《X战警：逆转未来》、《X战警：天启》");
                    break;
                case 15:
                    mProfile.setImageResource(R.drawable.card_item_mystique);
                    mNickName.setText("魔形女(Mystique)");
                    mName.setText("瑞雯·达克霍姆");
                    mBrief.setText("\"千变万化\"");
                    mIntro.setText("         魔形女（Mystique）是美国漫威漫画旗下的“反英雄”，" +
                            "初次登场于《惊奇女士》（Ms. Marvel）第16期（1978年4月）。\n" +
                            "         本名瑞雯·达克霍姆（Raven Darkholme），是一名变种人，" +
                            "夜行者的母亲，拥有可以变成任何人的超能力，以及改变自己身体器官的位置，" +
                            "以免在危急时刻，身体受到致命的伤害。此外，她还能延缓衰老，" +
                            "自称已经是一个年近200岁的人了，有一副好身手但同时又心狠手辣，" +
                            "是一个极具丰富色彩的人物。");
                    mMore.setText("知名对手：X战警" +
                            "\n相关电影：《X战警1》、《X战警2》、《X战警3：背水一战》、" +
                            "《X战警：逆转未来》、《X战警：天启》");
                    break;
                case 16:
                    mProfile.setImageResource(R.drawable.card_item_red_skull);
                    mNickName.setText("红骷髅(Red Skull)");
                    mName.setText("约翰·施密特");
                    mBrief.setText("\"纳粹九头蛇组织创始人\"");
                    mIntro.setText("         红骷髅（Red Skull）是美国漫威漫画旗下的超级反派，" +
                            "初次登场于《美国队长》（Capitain America）第7期（1941年10月），" +
                            "由杰克·科比、Joe Simon和France Herron联合创作。\n" +
                            "         本名约翰·施密特（Johann Shmidt），是纳粹党的特工和科学家，" +
                            "希特勒面前的红人，他与美国队长之间的战斗贯穿了整个二战，" +
                            "当美国队长掉入大海之中被冰封时，他也由于吸入了一种毒气而沉睡了近70年（漫画）。" +
                            "在漫威电影宇宙中，红骷髅在与美队的争斗中被宇宙立方传送至沃米尔行星" +
                            "（《美国队长：复仇者先锋》），" +
                            "在这里他受到诅咒被迫担任那些寻找灵魂原石的人的引路者。" +
                            "当灭霸和卡魔拉到达沃米尔的时候，红骷髅带领他们来到附近的断魂崖，" +
                            "并且告知灭霸必须牺牲自己某个所爱的人才能得到无限原石，" +
                            "而灭霸则流着泪将卡魔拉抛下悬崖，杀害了卡魔拉。由于除自己外不爱任何人，" +
                            "所以红骷髅无法为自己获得原石。导演罗素兄弟表示，在灭霸得到灵魂原石之后，" +
                            "红骷髅就从诅咒中解脱了，因此可以离开沃米尔行星，并且能为自己的欲望去寻得无限原石。");
                    mMore.setText("知名对手：美国队长" +
                            "\n相关电影：《美国队长：复仇者先锋》、《复仇者联盟3：无限战争》");
                    break;
                case 17:
                    mProfile.setImageResource(R.drawable.card_item_scarlet_witch);
                    mNickName.setText("绯红女巫(Scarlet Witch)");
                    mName.setText("旺达·姜戈·马克西莫夫");
                    mBrief.setText("\"No more mutants！\"");
                    mIntro.setText("         绯红女巫（Scarlet Witch）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《X战警》（The X-Men）第4期（1964年3月），" +
                            "由编剧斯坦·李和画家杰克·科比联合创造。\n" +
                            "         本名旺达·姜戈·马克西莫夫（Wanda Django Maximoff），" +
                            "她的起源故事版本众多，漫画中最新剧情是由吉普赛人姜戈和玛丽所生，" +
                            "小时候和双胞胎弟弟快银（比绯红女巫晚出生30秒）一起被高进化者" +
                            "（High Evolutionary）绑架当做实验对象，" +
                            "从而获得了混沌魔法（创造和湮灭物质）和修改概率的能力，" +
                            "后来二人被骗，听信了自己是万磁王的孩子，母亲抛弃了他们，" +
                            "接生的牛头女士把他们交给了吉普赛人夫妇的这个说法。" +
                            "从而与弟弟共同加入万磁王领导的变种人兄弟会。" +
                            "后得知真相改邪归正，加入复仇者联盟，并且遇到了同为复仇者成员的幻视，" +
                            "于是两人坠入爱河结为夫妻。在漫威电影宇宙中，" +
                            "电影《复仇者联盟2：奥创纪元》改编为快银的双胞胎妹妹（比快银晚出生12分钟），" +
                            "因与哥哥参与九头蛇的秘密试验并被洛基权杖上的宝石赋予超能力，" +
                            "她具有使用混沌魔法的超能力，潜力巨大，在打败奥创后加入复仇者联盟。");
                    mMore.setText("挚爱：幻视" +
                            "\n相关电影：《美国队长3：内战》、《复仇者联盟2：奥创纪元》、" +
                            "《复仇者联盟3：无限战争》");
                    break;
                case 18:
                    mProfile.setImageResource(R.drawable.card_item_silver_surfer);
                    mNickName.setText("银影侠(Silver Surfer)");
                    mName.setText("诺林·莱德");
                    mBrief.setText("\"四大吞星使者之一\"");
                    mIntro.setText("         银影侠（Silver Surfer）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《神奇四侠》第48期（1966年3月）。\n" +
                            "         本名诺林·莱德（Norrin Radd），" +
                            "是外银河系Zenn-La行星的一名天文学家，" +
                            "忽然有一天行星吞噬者（Galactus）降临在Zenn-La。他警告所有的居民说，" +
                            "此星球已被选上作为行星吞噬者的粮食。" +
                            "诺林试图与行星吞噬者谈条件，他乞求行星吞噬者放过他的母星。" +
                            "代价是诺林愿意永远服侍行星吞噬者，帮他寻找适合作为食物的星球，" +
                            "行星吞噬者考虑了一下得失后，他接受了诺林的条件，" +
                            "并赐给他一股强大的Cosmic Power，当诺林接触到这股力量的一瞬间，" +
                            "全身立刻生出了一层全银的外壳，并且连同记忆、善恶、良知、道德观等完全消失。" +
                            "行星吞噬者将诺林化身成银色滑翔者后，便命令他去找能吃的星球，" +
                            "而银色滑翔者因为记忆被消除的关系马上指着自己的母星说：“那颗可以吃”。" +
                            "但是行星吞噬者回答他说：“我跟一个值得尊敬的人约定过了，不能吃那颗”。\n" +
                            "         宇宙能量Cosmic Power赐予银影侠超人的气力，刀枪不入的身体及永恒的生命。" +
                            "银影侠只要与冲浪板相连就可以拥有无尽的宇宙能量，" +
                            "可以利用宇宙能量进行攻击、防御与飞翔。" +
                            "银影侠不需要吃饭、喝水、睡觉等常人补充能量的手段。");
                    mMore.setText("主人：行星吞噬者" +
                            "\n相关电影：《神奇四侠2：银影侠现身》");
                    break;
                case 19:
                    mProfile.setImageResource(R.drawable.card_item_spiderman);
                    mNickName.setText("蜘蛛侠(Spider-Man)");
                    mName.setText("彼得·本杰明·帕克");
                    mBrief.setText("\"With great power comes great responsibility\"");
                    mIntro.setText("         蜘蛛侠（Spider-Man）是美国漫威漫画旗下的超级英雄，" +
                            "由编剧斯坦·李和画家史蒂夫·迪特科联合创造，初次登场于《惊奇幻想》" +
                            "（Amazing Fantasy）第15期（1962年8月），因为广受欢迎，几个月后，" +
                            "便开始拥有以自己为主角的单行本漫画。\n" +
                            "         本名彼得·本杰明·帕克（Peter Benjamin Parker），" +
                            "是住在美国纽约皇后区的一名普通高中生，由于被一只受过放射性辐射的蜘蛛咬伤，" +
                            "因此获得了蜘蛛感应、蜘蛛力量、吸附能力等超能力，" +
                            "且耐力、反应、敏捷和速度、自愈能力都超乎常人，后自制蜘蛛战衣，" +
                            "化身蜘蛛侠（Spider-Man）守卫城市。漫威电影宇宙中蜘蛛战衣为钢铁侠打造，具有多种款式。\n" +
                            "         最初的漫威公司仅仅涉足漫画行业，不具备拍摄电影的资本，" +
                            "因此20世纪末漫威公司将大量英雄人物的电影翻拍权包括蜘蛛侠卖给了索尼、20世纪福克斯等电影公司，" +
                            "合同中有一项条款是“3年内不使用，该翻拍权将会自动收回”。" +
                            "但第一套真人电影即托比·马奎尔《蜘蛛侠》三部曲票房低迷，" +
                            "然而该英雄角色人气又居高不下，持有翻拍权的索尼公司只好不断重启该系列以此敛财，" +
                            "于是有了后面的大量蜘蛛侠电影。在漫威电影宇宙逐步成型后，" +
                            "索尼与漫威达成协议，共享蜘蛛侠版权，自此之后的蜘蛛侠电影加入漫威电影宇宙，票房热卖。");
                    mMore.setText("知名对手：绿魔、秃鹫、章鱼博士、金并" +
                            "\n相关电影：托比·马奎尔《蜘蛛侠》三部曲、《超凡蜘蛛侠》、" +
                            "《超凡蜘蛛侠2：电光人崛起》、动画电影《蜘蛛侠：平行宇宙》；" +
                            "漫威电影宇宙《蜘蛛侠：返校日》、《蜘蛛侠：远离故乡》、" +
                            "《美国队长3：内战》、《复仇者联盟3：无限战争》");
                    break;
                case 20:
                    mProfile.setImageResource(R.drawable.card_item_star_lord);
                    mNickName.setText("星爵(Star Lord)");
                    mName.setText("彼得·杰森·奎尔");
                    mBrief.setText("\"银河护卫队领袖\"");
                    mIntro.setText("         星爵（Star-Lord）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《Marvel Preview》第4期（1976年1月），" +
                            "由史蒂夫·英格哈特和史蒂夫·甘联合创造。\n" +
                            "         本名彼得·杰森·奎尔（Peter Jason Quill），" +
                            "在漫威电影宇宙中，彼得为天神族伊戈与地球人所生，" +
                            "在九岁的时侯，母亲因重病离世，此时被自号“破坏者”的太空流氓将其带离地球生活。" +
                            "长大后彼得自称“星爵”，专门盗取太空宝物。" +
                            "他的主要武器为元素枪，此外，还配备金属头盔及喷射推动装置于双脚，" +
                            "方便他在太空活动。他颈部上植有翻译装置，可翻译外星语言。" +
                            "目前与火箭浣熊、格鲁特等人组建了一支队伍——银河护卫队，" +
                            "与地球上的复仇者联盟联手，共同对抗宇宙级超级反派灭霸。");
                    mMore.setText("知名对手：伊戈" +
                            "\n相关电影：《银河护卫队》、《银河护卫队2》");
                    break;
                case 21:
                    mProfile.setImageResource(R.drawable.card_item_thor);
                    mNickName.setText("雷神(Thor)");
                    mName.setText("索尔·奥丁森");
                    mBrief.setText("\"阿斯加德王子\"");
                    mIntro.setText("         雷神索尔（Thor）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《神秘之旅》（Journey into Mystery）第83期（1962年8月），" +
                            "由斯坦·李、杰克·科比和拉里·利伯联合创造。\n" +
                            "         本名索尔·奥丁森（Thor Odinson），也是众神之父奥丁的长子，" +
                            "阿斯加德的王子。他拥有非凡的力量、速度、 耐力，寿命和感官，" +
                            "能够操纵雷电，他的著名武器为雷神之锤姆乔尔尼尔（Mjolnir）。" +
                            "在电影宇宙中经历了三个阶段后，体内的雷神之力逐渐觉醒，" +
                            "目前可使用暴风战斧击败极其六大无限宝石的灭霸。");
                    mMore.setText("知名对手：海拉、洛基（弟弟）" +
                            "\n相关电影：《雷神》、《雷神2：黑暗世界》、《雷神3：诸神黄昏》、" +
                            "《复仇者联盟》、《复仇者联盟2：奥创纪元》、《复仇者联盟3：无限战争》");
                    break;
                case 22:
                    mProfile.setImageResource(R.drawable.card_item_vemon);
                    mNickName.setText("毒液(Vemon)");
                    mName.setText("埃迪·布洛克");
                    mBrief.setText("\"蜘蛛侠宿敌\"");
                    mIntro.setText("         毒液（Venom）是美国漫威漫画旗下的“反英雄”，初次登场于" +
                            "《蜘蛛侠之网》（Web of Spider-Man）第18期（1986年9月）。\n" +
                            "         他是一种有思想的外星生命共生体，几乎以液体状的形式出现。" +
                            "它需要与一个宿主结合才能生存，并能赋予宿主强大的能力。" +
                            "当毒液共生体与宿主结合时，这两种生命形式的生命体才能被称作毒液，" +
                            "如果宿主是好人，可以让好人越战越勇，成为超级英雄，如果宿主是坏人，" +
                            "会使其变成超级反派。其中最著名的宿主就是爱德华·埃迪·布洛克（Edward Eddie Brock）。");
                    mMore.setText("知名对手：蜘蛛侠" +
                            "\n相关电影：《毒液：致命守护者》");
                    break;
                case 23:
                    mProfile.setImageResource(R.drawable.card_item_war_machine);
                    mNickName.setText("战争机器(War Machine)");
                    mName.setText("詹姆斯·罗德斯");
                    mBrief.setText("\"钢铁侠挚友\"");
                    mIntro.setText("         战争机器（War Machine）是美国漫威漫画旗下的超级英雄，" +
                            "初次登场于《钢铁侠》（Iron Man）第118期（1979年1月）。\n" +
                            "         本名詹姆斯·罗德斯（James Rhodes），" +
                            "是钢铁侠托尼·史塔克的好友、一名美军上校，也是美国陆军武器开发部的部长，" +
                            "还是斯塔克工业与美国军队的中间人。在漫威电影宇宙中，" +
                            "因与自觉命不久矣的托尼发生冲突，擅自穿上钢铁装甲Mark2与托尼大战后离开，" +
                            "在美国政府的协助下将此装甲的攻击系统加以改进，从此化身战争机器，为美国政府服务。" +
                            "后因一次敌人来袭，钢铁侠不敌，他与托尼并肩作战击溃对手，" +
                            "两人才重归于好，后加入复仇者联盟。此后托尼对此装甲进行过多次升级。");
                    mMore.setText("挚友：托尼·斯塔克" +
                            "\n相关电影：《钢铁侠2》、《钢铁侠3》、《美国队长3：内战》、" +
                            "《复仇者联盟2：奥创纪元》、《复仇者联盟3：无限战争》");
                    break;
                case 24:
                    mProfile.setImageResource(R.drawable.card_item_wolverine);
                    mNickName.setText("金刚狼(Wolverine)");
                    mName.setText("罗根·豪利特");
                    mBrief.setText("\"I'm the best there is at what I do, but what I do isn't very nice\"");
                    mIntro.setText("         罗根·豪利特（Logan Howlett）即金刚狼（Wolverine），" +
                            "是美国漫威漫画旗下的超级英雄，初次登场于《不可思议的浩克》" +
                            "（The Incredible Hulk）第180期（1974年10月），" +
                            "由莱恩·韦恩、约翰·罗密塔、贺伯特·林普联合创造。\n" +
                            "         又名詹姆斯·豪利特（James Howlett），" +
                            "十九世纪出生于加拿大的阿尔伯塔省，自从儿时发现自己的生父杀死自己的养父后，" +
                            "罗根的变种人能力便开始显现出来，他拥有延缓衰老和极强的自愈能力，" +
                            "双手还可以伸出利爪。后来被威廉·史崔克抓走并对其实施X武器计划，" +
                            "骨骼被注入艾德曼合金，手上的骨爪被无坚不摧的钢爪所取代。" +
                            "这使得罗根正式成为金刚狼（Wolverine），之后他加入了X战警和复仇者联盟等超级英雄团队。");
                    mMore.setText("知名对手：剑齿虎、万磁王" +
                            "\n相关电影：《金刚狼》、《金刚狼2》、《金刚狼3：殊死一战》、《X战警1》、" +
                            "《X战警2》、《X战警3：背水一战》、《X战警：逆转未来》");
                    break;
            }
        }
    }
}
